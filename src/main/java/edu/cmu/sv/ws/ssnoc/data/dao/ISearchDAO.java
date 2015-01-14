package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of User information in the system.
 * 
 */
public interface ISearchDAO {
	/**
	 * This method will search given userName into the database.
	 * 
	 * @param userName
	 *            - User information to be saved.
	 */
	List<UserPO> searchUserName(String userName);

	/**
	 * This method will search status in the
	 * database.
	 * 
	 * @return - List of all users.
	 */
	List<UserPO> searchStatus(String status);
	
	/**
	 * This method will search announce
	 * 
	 * @return - List of messages
	 */
	List<MessagePO> searchAnnouncement(String announcement);

	/**
	 * This method with search for messages on public wall
	 * 
	 * @param message
	 *            - message to search for.
	 * 
	 * @return - List of messages
	 */
	List<MessagePO> searchMessageOnPublicWall(String message);
	
	/**
	 * This method with search for messages as chat
	 * 
	 * @param message
	 *            - message to search for.
	 * 
	 * @return - List of messages
	 */
	List<MessagePO> searchMessageAsChat(String message);

}
