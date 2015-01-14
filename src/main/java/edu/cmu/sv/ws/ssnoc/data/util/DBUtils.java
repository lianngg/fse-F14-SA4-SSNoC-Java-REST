package edu.cmu.sv.ws.ssnoc.data.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.SSNCipher;
import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;

/**
 * This is a utility class to provide common functions to access and handle
 * Database operations.
 * 
 */
public class DBUtils{
	public static boolean DB_TABLES_EXIST = false;
	
	private static List<String> CREATE_TABLE_LST;

	static {
		CREATE_TABLE_LST = new ArrayList<String>();
		CREATE_TABLE_LST.add(SQL.CREATE_USERS);
		CREATE_TABLE_LST.add(SQL.CREATE_STATUSCRUMB);
		CREATE_TABLE_LST.add(SQL.CREATE_MESSAGE);
		CREATE_TABLE_LST.add(SQL.CREATE_MEMORY);
	}

	/**
	 * This method will initialize the database.
	 * 
	 * @throws SQLException
	 */
	public static void initializeDatabase() throws SQLException {
		createTablesInDB();
		createAdminInDB();
	}

	private static void createAdminInDB() {
		// TODO Auto-generated method stub
		Log.enter();

		UserPO userPO = new UserPO();
		userPO.setUserName("SSNAdmin");
		userPO.setPassword("admin");
		userPO.setPrivilege("Administrator");
		userPO.setLastStatusCode("OK");
		userPO.setAccountStatus("ACTIVE");
		userPO.setCreatedTimeStamp(new Date().getTime() / 1000);
		SSNCipher.encryptPassword(userPO);
		
		IUserDAO dao = DAOFactory.getInstance().getUserDAO();
		UserPO existingUser = dao.findByName("SSNAdmin");

		// Validation to check that user name should be unique
		// in the system. If a new users tries to register with
		// an existing userName, notify that to the user.
		if (existingUser != null) {
			Log.trace("Original Admin exists.");
			return;
		}
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_USER)) {
			stmt.setString(1, userPO.getUserName());
			stmt.setString(2, userPO.getPassword());
			stmt.setString(3, userPO.getSalt());
			stmt.setString(4, userPO.getLastStatusCode());
			stmt.setLong(5, userPO.getCreatedTimeStamp());
			stmt.setString(6, userPO.getPrivilege());
			stmt.setString(7, userPO.getAccountStatus());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Log.exit();
		}
	}

	/**
	 * This method will create necessary tables in the database.
	 * 
	 * @throws SQLException
	 */
	protected static void createTablesInDB() throws SQLException {
		Log.enter();
		if (DB_TABLES_EXIST) {
			return;
		}

		final String CORE_TABLE_NAME = SQL.SSN_USERS;

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
			if (!doesTableExistInDB(conn, CORE_TABLE_NAME)) {
				Log.info("Creating tables in database ...");

				for (String query : CREATE_TABLE_LST) {
					Log.debug("Executing query: " + query);
					boolean status = stmt.execute(query);
					Log.debug("Query execution completed with status: "
							+ status);
				}

				Log.info("Tables created successfully");
			} else {
				Log.info("Tables already exist in database. Not performing any action.");
			}

			DB_TABLES_EXIST = true;
		}
		Log.exit();
	}

	/**
	 * This method will check if the table exists in the database.
	 * 
	 * @param conn
	 *            - Connection to the database
	 * @param tableName
	 *            - Table name to check.
	 * 
	 * @return - Flag whether the table exists or not.
	 * 
	 * @throws SQLException
	 */
	public static boolean doesTableExistInDB(Connection conn, String tableName)
			throws SQLException {
		Log.enter(tableName);

		if (conn == null || tableName == null || "".equals(tableName.trim())) {
			Log.error("Invalid input parameters. Returning doesTableExistInDB() method with FALSE.");
			return false;
		}

		boolean tableExists = false;

		final String SELECT_QUERY = SQL.CHECK_TABLE_EXISTS_IN_DB;

		ResultSet rs = null;
		try (PreparedStatement selectStmt = conn.prepareStatement(SELECT_QUERY)) {
			selectStmt.setString(1, tableName.toUpperCase());
			rs = selectStmt.executeQuery();
			int tableCount = 0;
			if (rs.next()) {
				tableCount = rs.getInt(1);
			}

			if (tableCount > 0) {
				tableExists = true;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		Log.exit(tableExists);

		return tableExists;
	}

	/**
	 * This method returns a database connection from the Hikari CP Connection
	 * Pool
	 * 
	 * @return - Connection to the H2 database
	 * 
	 * @throws SQLException
	 */
	public static final Connection getConnection() throws SQLException {
		IConnectionPool cp = ConnectionPoolFactory.getInstance()
				.getH2ConnectionPool();
		return cp.getConnection();
	}
	
	/**
	 * This method will create performance messages table in the database.
	 * 
	 * @throws SQLException
	 */
	public static void createPerformanceMessageTablesInDB() throws SQLException {
		Log.enter();
		final String CORE_TABLE_NAME = SQL.SSN_PERFORMANCE_MESSAGE;

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
			if (!doesTableExistInDB(conn, CORE_TABLE_NAME)) {
				Log.info("Creating tables in database ...");

				Log.debug("Executing query: " + SQL.CREATE_PERFORMANCE_MESSAGE);
				boolean status = stmt.execute(SQL.CREATE_PERFORMANCE_MESSAGE);
				Log.debug("Query execution completed with status: "
						+ status);

				Log.info("Tables created successfully");
				
				//We also need to insert some initialization data in this database
				initializePerformanceMessageDB();
			} else {
				Log.info("Tables already exist in database. Not performing any action.");
			}
		}
		Log.exit();
	}
	
	protected static void initializePerformanceMessageDB() {
		Log.enter();
		MessagePO po = new MessagePO();
		
		try {
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();

			po.setMessageType("WALL");
			po.setAuthor("David");
			po.setContent("Hello everyone!");
			po.setPostedAt(new Date().getTime() / 1000);
			dao.save(po, SystemStateUtils.MEASUREPREFORMANCE);
			
			po.setMessageType("WALL");
			po.setAuthor("Lily");
			po.setContent("It's so cool!");
			po.setPostedAt(new Date().getTime() / 1000 + 60 * 5);
			dao.save(po, SystemStateUtils.MEASUREPREFORMANCE);
			
			po.setMessageType("WALL");
			po.setAuthor("Tom");
			po.setContent("That's true");
			po.setPostedAt(new Date().getTime() / 1000 + 60 * 10);
			dao.save(po, SystemStateUtils.MEASUREPREFORMANCE);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Log.exit();
		}

		return;
	}

	/**
	 * This method will drop performance messages table in the database.
	 * 
	 * @throws SQLException
	 */
	public static void dropPerformanceMessageTablesInDB() throws SQLException {
		Log.enter();
		final String CORE_TABLE_NAME = SQL.SSN_PERFORMANCE_MESSAGE;

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();) {
			if (doesTableExistInDB(conn, CORE_TABLE_NAME)) {
				Log.info("Droping tables in database ...");

				Log.debug("Executing query: " + SQL.DROP_PERFORMANCE_MESSAGE);
				boolean status = stmt.execute(SQL.DROP_PERFORMANCE_MESSAGE);
				Log.debug("Query execution completed with status: "
						+ status);
				doesTableExistInDB(conn, CORE_TABLE_NAME);

				Log.info("Tables dropped successfully");
			} else {
				Log.info("Tables do not exist in database. Not performing any action.");
			}
		}
		Log.exit();
	}
}
