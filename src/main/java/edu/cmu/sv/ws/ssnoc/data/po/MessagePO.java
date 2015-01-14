package edu.cmu.sv.ws.ssnoc.data.po;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all message information in the system.
 * This contains information like the message's ID, its content, its author, its messageType,
 * its target and the postAt information entered by the user when signing up. <br/>
 * Information is saved in SSN_MESSAGE table.
 * 
 */
public class MessagePO {
	private long messageId;
	private String content;
	private String author;
	private String messageType; //WALL and CHAT
	private String target;
	private long postedAt;
	
	public MessagePO(MessagePO message){
	    this.messageId = message.messageId;
	    this.content = message.content;
	    this.author = message.author;
	    this.messageType = message.messageType;
	    this.target = message.target;
	    this.postedAt = message.postedAt;
	}

	public MessagePO() {
        // TODO Auto-generated constructor stub
    }

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
        if (!(obj instanceof MessagePO))
            return false;
        MessagePO messagePO = (MessagePO) obj;
        if(!(messagePO.getAuthor().equals(this.getAuthor())))
            return false;
        if(!(messagePO.getContent().equals(this.getContent())))
            return false;
        if(!(messagePO.getMessageType().equals(this.getMessageType())))
            return false;
        if(!(messagePO.getPostedAt() == (this.getPostedAt())))
            return false; 
        return true;
    }

}
