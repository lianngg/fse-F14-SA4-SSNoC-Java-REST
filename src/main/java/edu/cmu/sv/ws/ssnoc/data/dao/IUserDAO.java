package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of User information in the system.
 * 
 */
public interface IUserDAO {
	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	void save(UserPO userPO);

	/**
	 * This method will load all the users in the
	 * database.
	 * 
	 * @return - List of all users.
	 */
	List<UserPO> loadUsers();
	
	/**
	 * This method will load visible users from the DB with specified account status. If
	 * no status information (null) is provided, it will load all users.
	 * 
	 * @return - List of users
	 */
	List<UserPO> loadUsersVisible();

	/**
	 * This method with search for a user by his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - UserPO with the user information if a match is found.
	 */
	UserPO findByName(String userName);

	/**
	 * This method will update the status information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be updated.
	 */
	void updateStatus(UserPO userPO);

	/**
	 * This method will update the record information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be updated.
	 */
	void updateRecord(UserPO userPO, String oldUserName);
}
