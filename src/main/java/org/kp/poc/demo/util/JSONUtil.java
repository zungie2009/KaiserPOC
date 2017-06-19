/**
 * 
 */
package org.kp.poc.demo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONObject;

/**
 * @author Robert Tu  06/18/2017
 *
 */
public class JSONUtil {
	
	public static JSONObject readJsonFromUrl(String url) throws Exception {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			String jsonText = sb.toString();
			return new JSONObject(jsonText);
		} finally {
			is.close();
		}
	}
	
	/*public static String parseJsonFromFeedMessage(FeedMessage feedMessagep) {
		StringBuilder sb = new StringBuilder();
		String cr = "\r\n";
		String tab = "\t";
		//String str = "\"values\"" ;
		//sb.append("");
		sb.append("{" + cr);
		Set<String> keys = map.keySet();
		for(String key : keys) {
			//System.out.println("key is "+key + "value is "+map.get(key));
			Object value = map.get(key);
			if(value == null) {
				value = new String("");
			}
			if(value instanceof Integer || value instanceof Long || value instanceof BigDecimal) {
				sb.append(tab + "\"" + key + "\":" + value.toString() + "," + cr);
			} else {
				sb.append(tab + "\"" + key + "\":\"" + value.toString() + "\"," + cr);
			}
		}
		String json = sb.toString().trim();
		if(json.endsWith(",")) {
			json = json.substring(0,  json.length() -1);
		}
		json = json + cr + "}" + cr;
		return json;
	}*/
}
