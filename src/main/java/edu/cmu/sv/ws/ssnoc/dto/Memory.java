package edu.cmu.sv.ws.ssnoc.dto;

import com.google.gson.Gson;

/**
 * This object contains memory information that is responded as part of the REST
 * API request.
 * 
 */
public class Memory {
	private long crumbID;
	private long usedVolatile;
	private long remainingVolatile;
	private long usedPersistent;
	private long remainingPersistent;
	private long createdAt;
	
	public long getCrumbID() {
		return crumbID;
	}

	public void setCrumbID(long crumbID) {
		this.crumbID = crumbID;
	}

	public long getUsedVolatile() {
		return usedVolatile;
	}

	public void setUsedVolatile(long usedVolatile) {
		this.usedVolatile = usedVolatile;
	}

	public long getRemainingVolatile() {
		return remainingVolatile;
	}

	public void setRemainingVolatile(long remainingVolatile) {
		this.remainingVolatile = remainingVolatile;
	}
	
	public long getCreatedTimeStamp() {
	    return createdAt;
	}
	
	public void setCreatedTimeStamp(long createdAt) {
	    this.createdAt = createdAt;
	}
	
    public long getUsedPersistent() {
        return usedPersistent;
    }
    
    public void setUsedPersistent(long usedPersistent) {
        this.usedPersistent = usedPersistent;
    }
    
    public long getRemainingPersistent() {
        return remainingPersistent;
    }
    
    public void setRemainingPersistent(long remainingPersistent) {
        this.remainingPersistent = remainingPersistent;
    }

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
