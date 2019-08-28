package com.vertx.config.web;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.http.HttpMethod;

public class WebServerConfig {
	private int port = 8090;
	private boolean isSSL = false;
	private String corsPattern = "*";
	private String certPath;
	private String certKeyPath;
	private boolean isCompressionSupported = false;
	private Set<String> allowedHeaders;
	private Set<HttpMethod> allowedMethods;
	
	
	
	public WebServerConfig() {
		this.allowedHeaders = new HashSet<>();
		this.allowedMethods = new HashSet<>();
		
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSSL() {
		return isSSL;
	}

	public void setSSL(boolean isSSL) {
		this.isSSL = isSSL;
	}

	public String getCorsPattern() {
		return corsPattern;
	}

	public void setCorsPattern(String corsPattern) {
		this.corsPattern = corsPattern;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getCertKeyPath() {
		return certKeyPath;
	}

	public void setCertKeyPath(String certKeyPath) {
		this.certKeyPath = certKeyPath;
	}

	public boolean isCompressionSupported() {
		return isCompressionSupported;
	}

	public void setCompressionSupported(boolean isCompressionSupported) {
		this.isCompressionSupported = isCompressionSupported;
	}

	public Set<String> getAllowedHeaders() {
		if(allowedHeaders.isEmpty()) {
			setDefaultHeaders();
		}
		return allowedHeaders;
	}
	
	public void addHeader(String header) {
		this.allowedHeaders.add(header);
	}
	
	public void addHttpMethod(HttpMethod method) {
		this.allowedMethods.add(method);
	}
	
	public void setAllowedHeaders(Set<String> allowedHeaders) {
		this.allowedHeaders = allowedHeaders;
	}

	public Set<HttpMethod> getAllowedMethods() {
		if(this.allowedMethods.isEmpty())
			setDefaultHttpMethods();
		
		return allowedMethods;
	}

	public void setAllowedMethods(Set<HttpMethod> allowedMethods) {
		this.allowedMethods = allowedMethods;
	}

	private void setDefaultHeaders() {
		allowedHeaders.add("Content-Type");
		allowedHeaders.add("Access-Control-Allow-Origin");
		allowedHeaders.add("Authorization");
	}
	
	private void setDefaultHttpMethods() {
		allowedMethods.add(HttpMethod.DELETE);
		allowedMethods.add(HttpMethod.POST);
		allowedMethods.add(HttpMethod.GET);
		allowedMethods.add(HttpMethod.PUT);
		allowedMethods.add(HttpMethod.OPTIONS);
	}
}
