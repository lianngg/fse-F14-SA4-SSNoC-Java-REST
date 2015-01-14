package edu.cmu.sv.ws.ssnoc.dto;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 * 
 */
public class Status {
    private long crumbID;
	private String userName;
	private String statusCode;
	private long createAt;
	
    public long getCrumbID() {
        return crumbID;
    }

    public void setCrumbID(long crumbID) {
        this.crumbID = crumbID;
    }
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public long getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}


	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
