package com.vertx.connectors.web;

import com.vertx.connectors.HttpAbstractServer;

public class HttpServerImpl extends HttpAbstractServer{
	private HttpSimpleService service;
	@Override
	public void loadRouter() {
		router.get("/api/data").handler(this.service::getData);
		router.post("/api/data").handler(this.service::saveData);
	}

	@Override
	public void init() {
		this.service = new HttpSimpleService(vertx);
	}

}
