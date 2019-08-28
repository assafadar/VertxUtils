package com.vertx.connectors.web;

import java.util.UUID;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class HttpSimpleService {
	private Vertx vertx;

	public HttpSimpleService(Vertx vertx) {
		this.vertx = vertx;
	}

	public void saveData(RoutingContext routingContext) {
		WorkerExecutor executor = vertx.createSharedWorkerExecutor("SAVE_DATA_"+UUID.randomUUID());
		executor.executeBlocking(task -> {
			try {
				
			}catch (Exception e) {
				task.fail(e);
			}finally {
				executor.close();
			}
		}, resultHandler -> {
			if(resultHandler.succeeded()) {
				JsonObject result = (JsonObject)resultHandler.result();
				routingContext.request().response()
					.setStatusCode(200).end(result.encode());
			}else {
				routingContext.request().response()
					.setStatusCode(500).end(resultHandler.cause().getMessage());
			}
		});
	}

	public void getData(RoutingContext routingContext) {
		System.out.println("In here");
		WorkerExecutor executor = vertx.createSharedWorkerExecutor("GET_DATA_"+UUID.randomUUID());
		
	}
}
