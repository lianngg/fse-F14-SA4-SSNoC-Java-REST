package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Message information in the system.
 * 
 */
public interface IMessageDAO {
	/**
	 * This method will save the information of the message into the database.
	 * 
	 * @param messagePO
	 *            - message information to be saved.
	 */
	void save(MessagePO messagePO, int a);

	/**
	 * This method will load all the messages
	 * on the public wall in the database.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> findMessagesOnWall();
	
	/**
	 * This method will load all visible messages
	 * on the public wall in the database.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> findMessagesOnWallVisible();
	
	/**
	 * This method will load all the messages
	 * as announcement.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> findMessagesAsAnnouncement();
	
	/**
	 * This method will load all the messages
	 * as announcement.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> findMessagesAsAnnouncementVisible();
	
	/**
	 * This method will load all the messages
	 * between two users in the database.
	 * @param userName1
	 *            - message information to be saved.
	 * @param userName2
	 *            - message information to be saved.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> findMessagesBetweenTwoUsers(String userName1, String userName2);
	
	/**
	 * This method will load all the messages
	 * between two users in the database.
	 * @param userName1
	 *            - message information to be saved.
	 * @param userName2
	 *            - message information to be saved.
	 * 
	 * @return - List of all messages.
	 */
	List<MessagePO> findMessagesBetweenTwoUsersVisible(String userName1, String userName2);

	/**
	 * This method with search for a user by his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - List<String> is a list of all chat buddies.
	 */
	List<String> findChatbuddiesByUser(String userName, long startTime, long endTime);

	/**
	 * This method will search for message by ID.
	 * 
	 * @param messageID
	 */
	MessagePO findMessageByID(long messageID);
}
