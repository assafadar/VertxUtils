package com.vertx.utils.config;

public class MessageConfig {
	private static int START_ERRORS = 500;
	private static int GENERAL_SUCCESS = 0;
	private static int GENERAL_ERROR = START_ERRORS;
	private static int SQL_SUCCESS = GENERAL_SUCCESS + 10;
	private static int SQL_ERRORS = START_ERRORS + 10;
	
	
	public enum MessageKey{
		OK(GENERAL_SUCCESS),
		
		
		
		
		DB_SQL_ERROR(SQL_ERRORS),
		DB_CLOSE_CONNECTION_ERROR(SQL_ERRORS+1),
		DB_CONNECTION_FAILED(SQL_ERRORS+2)
		;
		private int val;
		MessageKey(int val) {
			this.val = val;
		}
		
		public int getVal() {
			return this.val;
		}
	}
	
}
