package com.vertx.utils;

import com.vertx.utils.config.MessageConfig;
import com.vertx.utils.config.MessageConfig.MessageKey;

import io.vertx.core.json.JsonObject;

public class JsonUtils {
		
	public static JsonObject getErrorAsJson(MessageConfig.MessageKey messageKey, 
			Object errorMessage,Throwable e){
		return getMessageTemplate(messageKey)
				.put("descripion", errorMessage)
				.put("stack", e);

    }
	
	
	public static JsonObject getAsJson(MessageKey messageKey,String key,Object value) {
		return getMessageTemplate(messageKey).put(key, value);
	}
	private static JsonObject getMessageTemplate(MessageKey messageKey) {
		// TODO Auto-generated method stub
		return new JsonObject()
				.put("response_code", messageKey.getVal());
	}
}
