/**
 * 
 */
package org.kp.poc.demo.util;

import java.util.ArrayList;
import java.util.List;

import org.kp.poc.demo.model.Feed;
import org.kp.poc.demo.model.FeedMessage;

/**
 * @author Robert Tu   06/18/2017
 *
 */
public class RSSUtil {
	
	public static List<FeedMessage> getRSSFeed(String url) {
		RSSFeedParser parser = new RSSFeedParser(url);
        Feed feed = parser.readFeed();
        //System.out.println(feed);
        List<FeedMessage> feedList = new ArrayList<>();
        for (FeedMessage message : feed.getMessages()) {
            if(message.getLink().isEmpty()) {
            	message.setLink(message.getGuid());
            }
        	//System.out.println(message);
            feedList.add(message);
        }
        return feedList;
	}
}
