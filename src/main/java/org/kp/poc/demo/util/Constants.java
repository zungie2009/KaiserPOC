/**
 * 
 */
package org.kp.poc.demo.util;

/**
 * @author Robert Tu  06/18/2017
 *
 */
public class Constants {

	public static final String[] AUTHORIZED_URLS = { 
			"/",
			"/signup",
			"/login",
			"/submitLogin", 
			"/error", 
			"/js/(js}", 
			"/css/{css}",
			"/rest/wp", 
			"/rest/nyt",
			"/websocket/{*}"
	};
	
	public static final String USER_KEY = "APP_USER";
}
