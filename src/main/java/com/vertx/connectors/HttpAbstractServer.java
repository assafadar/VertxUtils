package com.vertx.connectors;

import com.vertx.config.web.WebServerConfig;
import com.vertx.connectors.web.IHttpService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

abstract public class HttpAbstractServer extends AbstractVerticle implements IHttpService{
	protected static Router router;
	private static WebServerConfig config;
	private static HttpServer server;
	
	
	
	protected HttpAbstractServer() {
		this(new WebServerConfig());
	}
	
	protected HttpAbstractServer(WebServerConfig config) {
		HttpAbstractServer.config = config;
	}
	
	@Override
	public void start(Future<Void> startFuture) {
		try {
			router = Router.router(vertx);
			init();
			configureRouter();
			configureHttpServer(startFuture);
			loadRouter();
		}catch (Exception e) {
			startFuture.fail(e);
		}
	}
	
	private void configureHttpServer(Future<Void> startFuture) throws Exception{
		HttpServerOptions httpOptions = new HttpServerOptions();
		if(config.isCompressionSupported())
			httpOptions.setCompressionSupported(config.isCompressionSupported());
		if(config.isSSL()) {
			httpOptions.setSsl(true);
			httpOptions.setPemKeyCertOptions(new PemKeyCertOptions()
					.setKeyPath(config.getCertKeyPath())
					.setCertPath(config.getCertPath()));
		}
		server = vertx.createHttpServer(httpOptions);
		server.requestHandler(router::accept)
			.listen(config.getPort(),res -> {
				if(res.succeeded()) {
					//Maybe not good.
					startFuture.complete();
				}else {
					startFuture.fail(res.cause());
				}
			});
	}
	
	private void configureRouter() throws Exception{
		router.route().handler(BodyHandler.create().setMergeFormAttributes(true));
		
		router.route().handler(CorsHandler.create(config.getCorsPattern())
				.allowedHeaders(config.getAllowedHeaders())
				.allowedMethods(config.getAllowedMethods()));
		
	}
}
