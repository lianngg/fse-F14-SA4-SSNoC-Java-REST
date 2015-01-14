package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class UserDAOImpl extends BaseDAOImpl implements IUserDAO {
	/**
	 * This method will load users from the DB with specified account status. If
	 * no status information (null) is provided, it will load all users.
	 * 
	 * @return - List of users
	 */
	public List<UserPO> loadUsers() {
		Log.enter();

		String query = SQL.FIND_ALL_USERS;

		List<UserPO> users = new ArrayList<UserPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			users = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(users);
		}

		return users;
	}
	
	/**
	 * This method will load visible users from the DB with specified account status. If
	 * no status information (null) is provided, it will load all users.
	 * 
	 * @return - List of users
	 */
	public List<UserPO> loadUsersVisible() {
		Log.enter();

		String query = SQL.FIND_ALL_USERS_VISIBLE;

		List<UserPO> users = new ArrayList<UserPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			users = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(users);
		}

		return users;
	}

	private List<UserPO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<UserPO> users = new ArrayList<UserPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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
	@Override
	public UserPO findByName(String userName) {
		Log.enter(userName);

		if (userName == null) {
			Log.warn("Inside findByName method with NULL userName.");
			return null;
		}

		UserPO po = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_USER_BY_NAME)) {
			stmt.setString(1, userName.toUpperCase());

			List<UserPO> users = processResults(stmt);

			if (users.size() == 0) {
				Log.debug("No user account exists with userName = " + userName);
			} else {
				po = users.get(0);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(po);
		}

		return po;
	}

	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	@Override
	public void save(UserPO userPO) {
		Log.enter(userPO);
		if (userPO == null) {
			Log.warn("Inside save method with userPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_USER)) {
			stmt.setString(1, userPO.getUserName());
			stmt.setString(2, userPO.getPassword());
			stmt.setString(3, userPO.getSalt());
			stmt.setString(4, "Undefined");
			stmt.setLong(5, userPO.getCreatedTimeStamp());
			stmt.setString(6, "Citizen");
			stmt.setString(7, "ACTIVE");

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	/**
	 * This method will update the stauts information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be updated.
	 */
	@Override
	public void updateStatus(UserPO userPO) {
		Log.enter(userPO);
		if (userPO == null) {
			Log.warn("Inside update method with userPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.UPDATE_STATUS_BY_NAME)) {
			stmt.setString(1, userPO.getLastStatusCode());
	        stmt.setLong(2, userPO.getModifiedTimeStamp());
			stmt.setString(3, userPO.getUserName());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows updated.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}
	
	/**
	 * This method will update the record information of the user into the database.
	 * 
	 * @param userPO
	 *            - User information to be updated.
	 */
	@Override
	public 	void updateRecord(UserPO userPO, String oldUserName) {
		Log.enter(userPO);
		if (userPO == null) {
			Log.warn("Inside update method with userPO == NULL");
			return;
		}
		try (Connection conn = getConnection();
		        PreparedStatement stmt = conn.prepareStatement(SQL.UPDATE_RECORD_BY_NAME)) {
			stmt.setString(1, userPO.getUserName());
			stmt.setString(2, userPO.getPassword());
			stmt.setString(3, userPO.getSalt());
			stmt.setLong(4, userPO.getModifiedTimeStamp());
			stmt.setString(5, userPO.getPrivilege());
			stmt.setString(6, userPO.getAccountStatus());
			stmt.setString(7, oldUserName);

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows updated.");
	        System.out.println("In updateUser(): "+oldUserName+"->"+userPO.getUserName());
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}
}
