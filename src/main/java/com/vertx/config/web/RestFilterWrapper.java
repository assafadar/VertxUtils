package com.vertx.config.web;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class RestFilterWrapper {
	private String filterPath;
	private Handler<RoutingContext> filter;
	
	public RestFilterWrapper(String filterPath, Handler<RoutingContext> filter) {
		this.filterPath = filterPath;
		this.filter = filter;
	}

	public String getFilterPath() {
		return filterPath;
	}

	public void setFilterPath(String filterPath) {
		this.filterPath = filterPath;
	}

	public Handler<RoutingContext> getFilter() {
		return filter;
	}

	public void setFilter(Handler<RoutingContext> filter) {
		this.filter = filter;
	}
	
	
}
