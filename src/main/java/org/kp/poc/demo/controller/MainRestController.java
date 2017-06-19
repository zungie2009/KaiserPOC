/**
 * 
 */
package org.kp.poc.demo.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kp.poc.demo.model.FeedMessage;
import org.kp.poc.demo.util.RSSUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Robert Tu  06/18/2017
 *
 */
@RestController
@RequestMapping("/rest")
public class MainRestController {
	private String wpUrl = "http://feeds.washingtonpost.com/rss/rss_post-nation";
	
	private String nytUrl = "http://rss.nytimes.com/services/xml/rss/nyt/Politics.xml";
	
	@RequestMapping(value = "/wp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FeedMessage>> processOutData(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
		  String headerName = (String)headerNames.nextElement();
		  System.out.println("***************processInData Found Header::: " + headerName + " : " + request.getHeader(headerName));
		}
		List<FeedMessage> feedList = RSSUtil.getRSSFeed(wpUrl);
		return new ResponseEntity<List<FeedMessage>>(feedList, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/nyt", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FeedMessage>> processInData(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
		  String headerName = (String)headerNames.nextElement();
		  System.out.println("***************processOutData Found Header::: " + headerName + " : " + request.getHeader(headerName));
		}
		List<FeedMessage> feedList = RSSUtil.getRSSFeed(nytUrl);
		return new ResponseEntity<List<FeedMessage>>(feedList, HttpStatus.CREATED);
	}
}
