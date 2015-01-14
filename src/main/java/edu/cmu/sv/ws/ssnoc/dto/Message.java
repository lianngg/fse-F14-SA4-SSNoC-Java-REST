package edu.cmu.sv.ws.ssnoc.dto;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 * 
 */
public class Message {
	private long messageId;
	private String content;
	private String author;
	private String messageType; //WALL, CHAT
	private String target;
	private long postedAt;
	
	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMessageType() {
		return messageType;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
    public String getTarget() {
        return target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }
    
    public long getPostedAt() {
        return postedAt;
    }
    
    public void setPostedAt(long postedAt) {
        this.postedAt = postedAt;
    }
    
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
	public int hashCode() {
		  assert false : "hashCode not designed";
		  return 42; // any arbitrary constant will do
		  }
	
	@Override
	public boolean equals(Object obj) {
	    if (!(obj instanceof Message))
	        return false;
	    Message message = (Message) obj;
	    if(!(message.getAuthor().equals(this.getAuthor())))
	        return false;
        if(!(message.getContent().equals(this.getContent())))
            return false;
        if(!(message.getMessageType().equals(this.getMessageType())))
            return false;
        if(!(message.getPostedAt() == (this.getPostedAt())))
            return false; 
	    return true;
	}

}
