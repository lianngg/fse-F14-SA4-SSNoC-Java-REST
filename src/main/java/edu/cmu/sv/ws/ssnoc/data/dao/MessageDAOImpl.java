package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

/**
 * DAO implementation for saving Message information in the H2 database.
 * 
 */
public class MessageDAOImpl extends BaseDAOImpl implements IMessageDAO {
	/**
	 * This method will save the information of the message into the database.
	 * 
	 * @param messagePO
	 *            - message information to be saved.
	 */
	public void save(MessagePO messagePO, int systemState) {
		Log.enter(messagePO, systemState);
		if (messagePO == null) {
			Log.warn("Inside save method with userPO == NULL");
			return;
		}

		PreparedStatement stmt;
		try (Connection conn = getConnection();) {
			if (systemState == SystemStateUtils.NORMAL) {
				stmt = conn.prepareStatement(SQL.INSERT_MESSAGE);
			}
			else {
				stmt = conn.prepareStatement(SQL.INSERT_PERFORMANCE_MESSAGE);
				SystemStateUtils.increasePostNumber();
			}
			stmt.setString(1, messagePO.getContent());
			stmt.setString(2, messagePO.getAuthor());
			stmt.setString(3, messagePO.getMessageType());
			stmt.setString(4, messagePO.getTarget());
			stmt.setLong(5, messagePO.getPostedAt());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	/**
	 * This method will load all the messages
	 * on the public wall in the database.
	 * 
	 * @return - List of all messages.
	 */
	public List<MessagePO> findMessagesOnWall() {
		Log.enter();

		List<MessagePO> messages = null;
		PreparedStatement stmt;
		try (Connection conn = getConnection();
						) {

			if (SystemStateUtils.getSystemState() == SystemStateUtils.NORMAL) {
				stmt = conn.prepareStatement(SQL.FIND_MESSAGES_ON_WALL);
			}
			else {
				stmt = conn.prepareStatement(SQL.FIND_PERFORMANCE_MESSAGES_ON_WALL);
				SystemStateUtils.increaseGetNumber();
			}
			messages = processResults(stmt);

			if (messages.size() == 0) {
				Log.debug("No messages on public wall");
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method will load all visible messages
	 * on the public wall in the database.
	 * 
	 * @return - List of all messages.
	 */
	public List<MessagePO> findMessagesOnWallVisible() {
		Log.enter();

		List<MessagePO> messages = null;
		PreparedStatement stmt;
		try (Connection conn = getConnection();) {
			stmt = conn.prepareStatement(SQL.FIND_MESSAGES_ON_WALL_VISIBLE);
			SystemStateUtils.increaseGetNumber();
			messages = processResults(stmt);

			if (messages.size() == 0) {
				Log.debug("No messages on public wall");
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method will load all the messages
	 * as announcement.
	 * 
	 * @return - List of all messages.
	 */
	public List<MessagePO> findMessagesAsAnnouncement() {
		Log.enter();

		List<MessagePO> messages = null;
		PreparedStatement stmt;
		try (Connection conn = getConnection();
						) {
			stmt = conn.prepareStatement(SQL.FIND_MESSAGES_AS_ANNOUNCEMENT);
			messages = processResults(stmt);

			if (messages.size() == 0) {
				Log.debug("No messages as announcement");
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method will load all the messages
	 * as announcement.
	 * 
	 * @return - List of all messages.
	 */
	public List<MessagePO> findMessagesAsAnnouncementVisible() {
		Log.enter();

		List<MessagePO> messages = null;
		PreparedStatement stmt;
		try (Connection conn = getConnection();
						) {
			stmt = conn.prepareStatement(SQL.FIND_MESSAGES_AS_ANNOUNCEMENT_VISIBLE);
			messages = processResults(stmt);

			if (messages.size() == 0) {
				Log.debug("No messages as announcement");
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}
	
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
	public List<MessagePO> findMessagesBetweenTwoUsers(String userName1, String userName2) {
		Log.enter(userName1, userName2);

		if (userName1 == null || userName2 == null) {
			Log.warn("Inside findMessagesBetweenTwoUsers method with NULL userName.");
			return null;
		}

		List<MessagePO> messages = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_MESSAGES_BY_TWO_USERS)) {
			stmt.setString(1, userName1.toUpperCase());
			stmt.setString(2, userName2.toUpperCase());
			stmt.setString(3, userName2.toUpperCase());
			stmt.setString(4, userName1.toUpperCase());

			messages = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}
	
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
	public List<MessagePO> findMessagesBetweenTwoUsersVisible(String userName1, String userName2) {
		Log.enter(userName1, userName2);

		if (userName1 == null || userName2 == null) {
			Log.warn("Inside findMessagesBetweenTwoUsers method with NULL userName.");
			return null;
		}

		List<MessagePO> messages = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_MESSAGES_BY_TWO_USERS_VISIBLE)) {
			stmt.setString(1, userName1.toUpperCase());
			stmt.setString(2, userName2.toUpperCase());
			stmt.setString(3, userName2.toUpperCase());
			stmt.setString(4, userName1.toUpperCase());

			messages = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}

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
	public List<String> findChatbuddiesByUser(String userName, long startTime, long endTime) {
		Log.enter(userName);

		if (userName == null) {
			Log.warn("Inside findChatbuddiesByUser method with NULL userName.");
			return null;
		}

		List<String> names = new ArrayList<String>();
		List<MessagePO> messages = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_CHATBUDDIES_BY_USER)) {
			stmt.setString(1, userName.toUpperCase());
			stmt.setString(2, userName.toUpperCase());
			stmt.setLong(3, startTime);
			stmt.setLong(4, endTime);

			messages = processResults(stmt);
			for (MessagePO message:messages) {
				names.add((message.getAuthor().equals(userName) ? message.getTarget() : message.getAuthor()));
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(names);
		}

		return names;
	}

	/**
	 * This method will search for message by ID.
	 * 
	 * @param messageID
	 */
	public MessagePO findMessageByID(long messageID) {
		Log.enter(messageID);

		if (messageID < 0) {
			Log.warn("Inside findMessageByID method with NULL userName.");
			return null;
		}

		List<MessagePO> messages = null;
		MessagePO message = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_MESSAGE_BY_ID)) {
			stmt.setLong(1, messageID);

			messages = processResults(stmt);
			if (messages.size() != 1) {
				Log.debug("Get findMessageByID num not equals to 1");
			}
			else {
				message = messages.get(0);
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(message);
		}

		return message;
	}
	
	private List<MessagePO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<MessagePO> messages = new ArrayList<MessagePO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				MessagePO po = new MessagePO();
				po = new MessagePO();
				po.setMessageId(rs.getLong(1));
				po.setContent(rs.getString(2));
				po.setAuthor(rs.getString(3));
				po.setMessageType(rs.getString(4));
				po.setTarget(rs.getString(5));
				po.setPostedAt(rs.getLong(6));

				messages.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
}
