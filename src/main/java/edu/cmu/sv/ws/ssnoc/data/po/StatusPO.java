package edu.cmu.sv.ws.ssnoc.data.po;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all status information in the system.
 * Information is saved in SSN_STATUSCRUMB table.
 * 
 */
public class StatusPO {
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
