package com.vertx.utils;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

public class DefaultConfiguration {
	
	public static JsonObject httpRouterConfig = new JsonObject()
			.put("allowedOriginPattern", "*")
			.put("headers",allowedHttpHeaders())
			.put("methods", allowedHttpMethods());

	public static Set<String> allowedHttpHeaders (){
		Set<String> headers = new HashSet<>();
		headers.add("Content-Type");
		headers.add("Access-Control-Allow-Origin");
		headers.add("Origin");
		return headers;
	} 
	
	public static Set<HttpMethod> allowedHttpMethods(){
		Set<HttpMethod> methods = new HashSet<>();
		methods.add(HttpMethod.DELETE);
		methods.add(HttpMethod.GET);
		methods.add(HttpMethod.POST);
		methods.add(HttpMethod.PUT);
		methods.add(HttpMethod.OPTIONS);
		return methods;
	}
}
