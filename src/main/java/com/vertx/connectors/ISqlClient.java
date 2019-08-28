package com.vertx.connectors;

import java.util.function.Function;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface ISqlClient {
	
    void callProcedure(Function<JsonObject, Void> nextMethod, String sql, JsonArray in, 
    		JsonArray out, String id) throws Exception;
    void selectFunction(Function<JsonObject, Void> nextMethod, String sql)throws Exception;

	void close();
}
