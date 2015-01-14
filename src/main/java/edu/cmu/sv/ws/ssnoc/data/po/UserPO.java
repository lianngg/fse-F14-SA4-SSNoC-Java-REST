package edu.cmu.sv.ws.ssnoc.data.po;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all user information in the system.
 * This contains information like the user's name, his role, his account status
 * and the password information entered by the user when signing up. <br/>
 * Information is saved in SSN_USERS table.
 * 
 */
public class UserPO {
	private long userId;
	private String userName;
	private String password;
	private String salt;
	private long createdAt;
	private long modifiedAt;
	private String lastStatusCode;//GREEN, YELLOW, RED AND UNDIFINED
	private String privilege;
	private String accountStatus;
	
	public UserPO(UserPO user){
	    this.userId = user.userId;
	    this.userName = user.userName;
	    this.password = user.password;
	    this.salt = user.salt;
	    this.lastStatusCode = user.lastStatusCode;
	    this.privilege = user.privilege;
	    this.accountStatus = user.accountStatus;
	}

	public UserPO() {
        // TODO Auto-generated constructor stub
    }

    public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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

	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
    public long getCreatedTimeStamp() {
        return createdAt;
    }
    
    public void setCreatedTimeStamp(long createdAt) {
        this.createdAt = createdAt;
    }
    
    public long getModifiedTimeStamp() {
        return modifiedAt;
    }
    
    public void setModifiedTimeStamp(long modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
	
	public String getLastStatusCode() {
		return lastStatusCode;
	}
	
	public void setLastStatusCode(String lastStatusCode) {
		this.lastStatusCode = lastStatusCode;
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
        if (!(obj instanceof UserPO))
            return false;
        UserPO user = (UserPO) obj;
//        if(!(user.getCreatedTimeStamp() == (this.getCreatedTimeStamp())))
//            return false;
        if(!(user.getUserName().equals(this.getUserName())))
            return false;
        return true;
    }

}
