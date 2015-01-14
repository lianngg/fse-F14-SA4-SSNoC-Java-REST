package edu.cmu.sv.ws.ssnoc.dto;

import com.google.gson.Gson;

/**
 * This object contains user information that is responded as part of the REST
 * API request.
 * 
 */
public class User {
	private String userName;
	private String password;
	private long createdAt;
	private long modifiedAt;
	private String lastStatusCode;
	private String privilege;
	private String accountStatus;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getCreatedAt() {
	    return createdAt;
	}
	
	public void setCreatedAt(long createdAt) {
	    this.createdAt = createdAt;
	}
	
    public long getModifiedAt() {
        return modifiedAt;
    }
    
    public void setModifiedAt(long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    public String getStatus() {
        return lastStatusCode;
    }
    
    public void setStatus(String color) {
        this.lastStatusCode = color;
    }

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
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
        if (!(obj instanceof User))
            return false;
        User user = (User) obj;
//        if(!(user.getCreatedTimeStamp() == (this.getCreatedTimeStamp())))
//            return false;
        if(!(user.getUserName().equals(this.getUserName())))
            return false;
	    return true;
	}

}
