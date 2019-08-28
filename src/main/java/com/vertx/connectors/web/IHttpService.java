package com.vertx.connectors.web;

import io.vertx.core.AbstractVerticle;

public interface IHttpService {
	static AbstractVerticle create() {
		return new HttpServerImpl();
	}
	
	void loadRouter();
	void init();
}
