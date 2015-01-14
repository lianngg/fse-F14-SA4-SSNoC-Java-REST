package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class SearchDAOImpl extends BaseDAOImpl implements ISearchDAO {
	/**
	 * This method will search given userName into the database.
	 * 
	 * @param userName
	 *            - User information to be saved.
	 */
	public List<UserPO> searchUserName(String userName) {
		final String SEARCH_USERS = "select user_id, user_name, password,"
				+ " salt , lastStatusCode, createdAt, modifiedAt, privilege, accountStatus"
				+ " from  SSN_USERS"
				+ " where user_name like \'%" + userName + "%\'  AND accountStatus = 'ACTIVE'";
		List<UserPO> users = new ArrayList<UserPO>();
		String searchUsersStmt = SEARCH_USERS;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(searchUsersStmt);) {
			users = processResultsUser(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(users);
		}
		
		return users;
	}

	/**
	 * This method will search status in the
	 * database.
	 * 
	 * @return - List of all users.
	 */
	public List<UserPO> searchStatus(String status) {
		final String SEARCH_STATUS = "select user_id, user_name, password,"
				+ " salt , lastStatusCode, createdAt, modifiedAt, privilege, accountStatus"
				+ " from  SSN_USERS"
				+ " where lastStatusCode like \'%" + status + "%\'"
				+ " AND accountStatus = 'ACTIVE'";
		String searchStatusStmt = SEARCH_STATUS;
		List<UserPO> users = new ArrayList<UserPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(searchStatusStmt);) {
			users = processResultsUser(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(users);
		}
		
		return users;
	}
	
	/**
	 * This method will search announce
	 * 
	 * @return - List of messages
	 */
	public List<MessagePO> searchAnnouncement(String announcement) {
		final String SEARCH_ANNOUNCEMENT = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
				+ "SSN_USERS.user_name, SSN_MESSAGE.messageType, SSN_USERS.user_name, SSN_MESSAGE.postedAt"
				+ " from "
				+ "SSN_MESSAGE, SSN_USERS"
				+ " where SSN_MESSAGE.messageType = 'ANNOUNCEMENT' AND SSN_MESSAGE.authorID = SSN_USERS.user_id AND SSN_USERS.accountStatus = 'ACTIVE'";
		String[] searchConntent = announcement.split(" ");
		String searchAnnounceStmt = SEARCH_ANNOUNCEMENT;
		for (int i = 0; i < searchConntent.length; i ++) {
			searchAnnounceStmt = searchAnnounceStmt + " AND SSN_MESSAGE.content like \'%" + searchConntent[i] + "%\'";
		}
		
		List<MessagePO> messages = null;
		PreparedStatement stmt;
		try (Connection conn = getConnection();
						) {
			stmt = conn.prepareStatement(searchAnnounceStmt);
			messages = processResultsMessage(stmt);

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
	 * This method with search for messages on public wall
	 * 
	 * @param message
	 *            - message to search for.
	 * 
	 * @return - List of messages
	 */
	public List<MessagePO> searchMessageOnPublicWall(String message) {
		final String SEARCH_MESSAGE_ON_PUBLIC_WALL = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
				+ "SSN_USERS.user_name, SSN_MESSAGE.messageType, SSN_USERS.user_name, SSN_MESSAGE.postedAt"
				+ " from "
				+ "SSN_MESSAGE, SSN_USERS"
				+ " where SSN_MESSAGE.messageType = 'WALL' AND SSN_MESSAGE.authorID = SSN_USERS.user_id AND SSN_USERS.accountStatus = 'ACTIVE'";
		String[] searchConntent = message.split(" ");
		String searchWallStmt = SEARCH_MESSAGE_ON_PUBLIC_WALL;
		for (int i = 0; i < searchConntent.length; i ++) {
			searchWallStmt = searchWallStmt + " AND SSN_MESSAGE.content like \'%" + searchConntent[i] + "%\'";
		}
		
		List<MessagePO> messages = null;
		PreparedStatement stmt;
		try (Connection conn = getConnection();
						) {
			stmt = conn.prepareStatement(searchWallStmt);
			messages = processResultsMessage(stmt);

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
	 * This method with search for messages as chat
	 * 
	 * @param message
	 *            - message to search for.
	 * 
	 * @return - List of messages
	 */
	public List<MessagePO> searchMessageAsChat(String chat) {
		final String SEARCH_MESSAGE_AS_CHAT = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
				+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
				+ " from "
				+ "SSN_MESSAGE, SSN_USERS User1, SSN_USERS User2 "
				+ " where ((SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE')"
				+ " OR (SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE'))";
		String[] searchConntent = chat.split(" ");
		String searchChatStmt = SEARCH_MESSAGE_AS_CHAT;
		for (int i = 0; i < searchConntent.length; i ++) {
			searchChatStmt = searchChatStmt + " AND SSN_MESSAGE.content like \'%" + searchConntent[i] + "%\'";
		}
		
		List<MessagePO> messages = null;
		PreparedStatement stmt;
		try (Connection conn = getConnection();
						) {
			stmt = conn.prepareStatement(searchChatStmt);
			messages = processResultsMessage(stmt);

			if (messages.size() == 0) {
				Log.debug("No messages on public wall");
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(messages);
		}

		return messages;
	}

	private List<UserPO> processResultsUser(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<UserPO> users = new ArrayList<UserPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				UserPO po = new UserPO();
				po = new UserPO();
				po.setUserId(rs.getLong(1));
				po.setUserName(rs.getString(2));
				po.setPassword(rs.getString(3));
				po.setSalt(rs.getString(4));
				po.setLastStatusCode(rs.getString(5));
				po.setCreatedTimeStamp(rs.getLong(6));
				po.setModifiedTimeStamp(rs.getLong(7));
				po.setPrivilege(rs.getString(8));
				po.setAccountStatus(rs.getString(9));

				users.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}
	
	private List<MessagePO> processResultsMessage(PreparedStatement stmt) {
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
