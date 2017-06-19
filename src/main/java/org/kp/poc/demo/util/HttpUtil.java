/**
 * 
 */
package org.kp.poc.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robert Tu  06/18/2017
 *
 */
public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public static String postJson(String json, String urlStr) throws MalformedURLException, IOException {
		URL url = new URL(urlStr);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		//connection.setRequestProperty("Authorization", "Basic " + encoding);
		connection.setRequestProperty("cache-control", "no-cache");
		connection.setRequestProperty("CharSet", "UTF-8");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		out.write(json);
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder sb = new StringBuilder();
		String line = "";
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		logger.info("REST JSON Service Invoked Successfully..");
		in.close();
		return sb.toString();
	}
}
