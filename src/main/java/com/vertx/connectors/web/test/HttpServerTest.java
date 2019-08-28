package com.vertx.connectors.web.test;

import com.vertx.connectors.web.IHttpService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class HttpServerTest {

	public static void main(String[] args) {
		AbstractVerticle verticle = IHttpService.create();
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(verticle);
	}

}
