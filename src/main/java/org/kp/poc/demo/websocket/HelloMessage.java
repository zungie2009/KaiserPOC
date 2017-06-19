/**
 * 
 */
package org.kp.poc.demo.websocket;

/**
 * @author Robert Tu  06/18/2017
 *
 */
public class HelloMessage {

    private String path;
	private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getJSON() {
    	return name;
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
    
}
