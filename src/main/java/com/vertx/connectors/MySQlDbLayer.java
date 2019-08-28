package com.vertx.connectors;

import java.util.ArrayList;
import java.util.function.Function;

import com.vertx.utils.JsonUtils;
import com.vertx.utils.LogUtils;
import com.vertx.utils.config.MessageConfig;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class MySQlDbLayer implements ISqlClient{
	
	private SQLClient sqlClient;
		
	public MySQlDbLayer(Vertx vertx, JsonObject config) {
		connect(vertx,config);
	}
	
	
	public void connect(Vertx vertx, JsonObject config) {
		sqlClient = JDBCClient.createShared(vertx, config);
	}
	
	public void callProcedure(Function<JsonObject, Void> nextMethod, String sql, JsonArray in, JsonArray out, String id)
			throws Exception {
		sqlClient.getConnection(con -> {
			if (con.succeeded()) {
				final SQLConnection connection = con.result();
				connection.callWithParams(sql, in, out, res -> {
					String outStr = "";
					if (res.succeeded()) {
						ResultSet result = res.result();
						JsonObject jsonObject = new JsonObject().put("data", result.getOutput());
						try {
							jsonObject = getOutSlashes(jsonObject.toString());
							nextMethod.apply(JsonUtils.getAsJson(MessageConfig.MessageKey.OK,"sql", sql)
									.put("id", id).put("data", jsonObject.getJsonArray("data")));
						} catch (Exception e) {
							nextMethod.apply(JsonUtils.getErrorAsJson(MessageConfig.MessageKey.DB_SQL_ERROR, sql,e )
									.put("data", jsonObject));
						}
					} else {
						nextMethod.apply(JsonUtils.getAsJson(MessageConfig.MessageKey.DB_SQL_ERROR,
								"Failed to get data " + res.cause().getMessage() + " IN=" + in + " OUT=" + out,
								res.cause()));
					}
					connection.close(done -> {
						if (done.failed()) {
							LogUtils.logError(MessageConfig.MessageKey.DB_CLOSE_CONNECTION_ERROR,
									"Close connection error", done.cause());
						}

					});
				});
			} else {
				nextMethod.apply(JsonUtils.getErrorAsJson(
						MessageConfig.MessageKey.DB_CONNECTION_FAILED,
						"Failed to get connection " + sql + " : " + 
						con.cause().getMessage(), con.cause()));
			}
		});
		
	}

	public void selectFunction(Function<JsonObject, Void> nextMethod, String sql) throws Exception {
		sqlClient.getConnection(con -> {
			if (con.succeeded()) {
				SQLConnection connection = con.result();

				connection.query(sql, res -> {
					if (res.succeeded()) {
						ResultSet rs = res.result();
						JsonArray outList = new JsonArray(rs.getRows());
						JsonObject jsonObject = outList.getJsonObject(0);
						String outStr = jsonObject.getString("res");
						JsonObject outResult = new JsonObject();
						try {
							jsonObject = getOutSlashes(outStr);
							nextMethod.apply(JsonUtils.getAsJson(MessageConfig.MessageKey.OK,"sql", sql)
									.put("data",jsonObject));
						} catch (Exception e) {
							nextMethod
									.apply(JsonUtils
											.getAsJson(MessageConfig.MessageKey.DB_SQL_ERROR,
													e.getMessage() + " for sql: " + sql,e)
											.put("data", jsonObject));
						}
					} else {
						nextMethod.apply(JsonUtils.getAsJson(MessageConfig.MessageKey.DB_SQL_ERROR,
								"Failed to get data " + res.cause().getMessage() + " SQL=" + sql, res.cause()));
					}
					connection.close(done -> {
						if (done.failed()) {
							throw new IllegalStateException("Faield closing DB connection "+done.cause());
						}

					});

				});
			} else {

				nextMethod.apply(JsonUtils.getAsJson(MessageConfig.MessageKey.DB_CONNECTION_FAILED,
						"Failed to get connection " + con.cause().getMessage(), con.cause()));
			}
		});
	}

	public void close() {
		sqlClient.close();
	}
	
	
	private JsonObject getOutSlashes(String outVal) throws Exception {
		StringBuilder fullVal = null;
		try {
			String outStr = "";
			outStr = outVal.toString().replaceAll("\\\\", "");
			outStr = outStr.replaceAll("}\"", "}");
			outStr = outStr.replaceAll("]\"", "]");

			final char[] chars = outStr.toCharArray();
			final int len = chars.length;
			ArrayList<String> subVal = new ArrayList<>();
			int lastCat = 0;
			for (int i = 0; i < len; i++) {
				if (i > 1 && (chars[i] == '{' || chars[i] == '[') && chars[i - 1] == '"') {
					subVal.add(outStr.substring(lastCat, i - 1));
					lastCat = i;
				}
			}
			subVal.add(outStr.substring(lastCat, len));
			fullVal = new StringBuilder();
			StringBuilder finalFullVal = fullVal;
			subVal.forEach(item -> finalFullVal.append(item));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new JsonObject(fullVal.toString());

	}
	
}


