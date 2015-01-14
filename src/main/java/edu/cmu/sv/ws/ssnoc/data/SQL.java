package edu.cmu.sv.ws.ssnoc.data;

/**
 * This class contains all the SQL related code that is used by the project.
 * Note that queries are grouped by their purpose and table associations for
 * easy maintenance.
 * 
 */
public class SQL {
	/*
	 * List the USERS table name, and list all queries related to this table
	 * here.
	 */
	public static final String SSN_USERS = "SSN_USERS";
	public static final String SSN_STATUSCRUMB = "SSN_STATUSCRUMB";
	public static final String SSN_MESSAGE = "SSN_MESSAGE";
	public static final String SSN_MEMORYCRUMB = "SSN_MEMORYCRUMB";
	public static final String SSN_PERFORMANCE_MESSAGE = "SSN_PERFORMANCE_MESSAGE";

	/**
	 * Query to check if a given table exists in the H2 database.
	 */
	public static final String CHECK_TABLE_EXISTS_IN_DB = "SELECT count(1) as rowCount "
			+ " FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = SCHEMA() "
			+ " AND UPPER(TABLE_NAME) = UPPER(?)";

	// ****************************************************************
	// All queries related to USERS
	// ****************************************************************
	/**
	 * Query to create the USERS table.
	 */
	public static final String CREATE_USERS = "create table IF NOT EXISTS "
			+ SSN_USERS + " ( user_id IDENTITY PRIMARY KEY,"
			+ " user_name VARCHAR(100)," + " password VARCHAR(512),"
			+ " salt VARCHAR(512) , lastStatusCode VARCHAR(100), createdAt BIGINT,"
			+ "modifiedAt BIGINT, privilege VARCHAR(100), accountStatus VARCHAR(100))";

	/**
	 * Query to load all users in the system.
	 */
	public static final String FIND_ALL_USERS = "select user_id, user_name, password,"
			+ " salt , lastStatusCode, createdAt, modifiedAt, privilege, accountStatus"
			+ " from " + SSN_USERS
			+ " order by user_name";
	
	/**
	 * Query to load all users in the system.
	 */
	public static final String FIND_ALL_USERS_VISIBLE = "select user_id, user_name, password,"
			+ " salt , lastStatusCode, createdAt, modifiedAt, privilege, accountStatus"
			+ " from " + SSN_USERS
			+ " where accountStatus = 'ACTIVE'"
			+ " order by user_name";

	/**
	 * Query to find a user details depending on his name. Note that this query
	 * does a case insensitive search with the user name.
	 */
	public static final String FIND_USER_BY_NAME = "select user_id, user_name, password,"
			+ " salt , lastStatusCode, createdAt, modifiedAt, privilege, accountStatus"
			+ " from "
			+ SSN_USERS
			+ " where UPPER(user_name) = UPPER(?)";

	/**
	 * Query to insert a row into the users table.
	 */
	public static final String INSERT_USER = "insert into " + SSN_USERS
			+ " (user_name, password, salt, lastStatusCode, createdAt, privilege, accountStatus) values (?, ?, ?, ?, ?, ?, ?)";
	
	/**
	 * Query to update status in a row into the users table.
	 */
	public static final String UPDATE_STATUS_BY_NAME = "update " + SSN_USERS
			+ " set lastStatusCode = ?, modifiedAt=?"
			+ " where UPPER(user_name) = UPPER(?) AND accountStatus = 'ACTIVE'";
	
	/**
	 * Query to update username, password and modifiedAt in a row into the users table.
	 */
	public static final String UPDATE_RECORD_BY_NAME = "update " + SSN_USERS
			+ " set user_name = ?"
			+ " ,password = ?"
			+ " ,salt = ?"
			+ " ,modifiedAt = ?"
			+ " ,privilege = ?"
			+ " ,accountStatus = ?"
			+ " where UPPER(user_name) = UPPER(?)";

	
	// ****************************************************************
	// All queries related to SSN_STATUSCRUMB
	// ****************************************************************
	/**
	 * Query to create the STATUSCRUMB table.
	 */
//	public static final String CREATE_STATUSCRUMB = "create table IF NOT EXISTS "
//			+ SSN_STATUSCRUMB + " ( crumbID IDENTITY PRIMARY KEY,"
//			+ " user_name VARCHAR(100)," + " statusCode VARCHAR(100),"
//			+ " createAt BIGINT )";
	
	public static final String CREATE_STATUSCRUMB = "create table IF NOT EXISTS "
			+ SSN_STATUSCRUMB + " ( crumbID IDENTITY PRIMARY KEY,"
			+ " userID BIGINT," + " statusCode VARCHAR(100),"
			+ " createAt BIGINT )";
	
	/**
	 * Query to find a statuscrumb details depending on his name. Note that this query
	 * does a case insensitive search with the user name.
	 */
//	public static final String FIND_STATUSCRUMB_BY_NAME = "select crumbID, user_name, statusCode,"
//			+ " createAt "
//			+ " from "
//			+ SSN_STATUSCRUMB
//			+ " where UPPER(user_name) = UPPER(?)";
	
	public static final String FIND_STATUSCRUMB_BY_NAME = "select SSN_STATUSCRUMB.crumbID, "
			+ "SSN_USERS.user_name, SSN_STATUSCRUMB.statusCode,"
			+ " SSN_STATUSCRUMB.createAt "
			+ " from "
			+ SSN_STATUSCRUMB + ", " + SSN_USERS
			+ " where UPPER(SSN_USERS.user_name) = UPPER(?) AND SSN_STATUSCRUMB.userID = SSN_USERS.user_id AND SSN_USERS.accountStatus = 'ACTIVE'";
	
	/**
	 * Query to find a statuscrumb details depending on its crumbID. Note that this query
	 * does a case insensitive search with the user name.
	 */
//	public static final String FIND_STATUSCRUMB_BY_ID = "select crumbID, user_name, statusCode,"
//			+ " createAt "
//			+ " from "
//			+ SSN_STATUSCRUMB
//			+ " where crumbID = ?";
	
	public static final String FIND_STATUSCRUMB_BY_ID = "select SSN_STATUSCRUMB.crumbID,"
			+ " SSN_USERS.user_name, SSN_STATUSCRUMB.statusCode,"
			+ " SSN_STATUSCRUMB.createAt "
			+ " from "
			+ SSN_STATUSCRUMB + ", " + SSN_USERS
			+ " where crumbID = ? AND SSN_STATUSCRUMB.userID = SSN_USERS.user_id AND SSN_USERS.accountStatus = 'ACTIVE'";
	
	/**
	 * Query to insert a row into the statuscrumb table.
	 */
//	public static final String INSERT_STATUSCRUMB = "insert into " + SSN_STATUSCRUMB
//			+ " (user_name, statusCode, createAt) values (?, ?, ?)";
	
	public static final String INSERT_STATUSCRUMB = "insert into " + SSN_STATUSCRUMB
			+ " (userID, statusCode, createAt) values "
			+ "((select user_id from SSN_USERS where user_name = ? AND accountStatus = 'ACTIVE'), ?, ?)";
	
	// ****************************************************************
	// All queries related to MESSAGE
	// ****************************************************************
	/**
	 * Query to create the MESSAGE table.
	 */
//	public static final String CREATE_MESSAGE = "create table IF NOT EXISTS "
//			+ SSN_MESSAGE + " ( message_id IDENTITY PRIMARY KEY,"
//			+ " content VARCHAR(512)," + " author VARCHAR(100),"
//			+ " messageType VARCHAR(100) , target VARCHAR(100), postedAt BIGINT)";
	
	public static final String CREATE_MESSAGE = "create table IF NOT EXISTS "
			+ SSN_MESSAGE + " ( message_id IDENTITY PRIMARY KEY,"
			+ " content VARCHAR(512)," + " authorID BIGINT,"
			+ " messageType VARCHAR(100) , targetID BIGINT, postedAt BIGINT)";

	/**
	 * Query to insert message in the system.
	 */
//	public static final String INSERT_MESSAGE = "insert into " + SSN_MESSAGE
//			+ " (content, author, messageType, target, postedAt) values (?, ?, ?, ?, ?)";
	
	public static final String INSERT_MESSAGE = "insert into " + SSN_MESSAGE
			+ " (content, authorID, messageType, targetID, postedAt) values"
			+ " (?, (select user_id from SSN_USERS where user_name = ? AND accountStatus = 'ACTIVE'), ?, (select user_id from SSN_USERS where user_name = ? AND accountStatus = 'ACTIVE'), ?)";
	
	/**
	 * Query to find messages on wall
	 */
	public static final String FIND_MESSAGES_ON_WALL = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "SSN_USERS.user_name, SSN_MESSAGE.messageType, SSN_USERS.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS
			+ " where SSN_MESSAGE.messageType = 'WALL' AND SSN_MESSAGE.authorID = SSN_USERS.user_id"
			+ " order by SSN_MESSAGE.postedAt DESC";
	
	/**
	 * Query to find messages on wall
	 */
	public static final String FIND_MESSAGES_ON_WALL_VISIBLE = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "SSN_USERS.user_name, SSN_MESSAGE.messageType, SSN_USERS.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS
			+ " where SSN_MESSAGE.messageType = 'WALL' AND SSN_MESSAGE.authorID = SSN_USERS.user_id AND SSN_USERS.accountStatus = 'ACTIVE'"
			+ " order by SSN_MESSAGE.postedAt DESC";
	
	/**
	 * Query to find messages on wall
	 */
	public static final String FIND_MESSAGES_AS_ANNOUNCEMENT = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "SSN_USERS.user_name, SSN_MESSAGE.messageType, SSN_USERS.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS
			+ " where SSN_MESSAGE.messageType = 'ANNOUNCEMENT' AND SSN_MESSAGE.authorID = SSN_USERS.user_id"
			+ " order by SSN_MESSAGE.postedAt DESC";
	
	/**
	 * Query to find messages on wall
	 */
	public static final String FIND_MESSAGES_AS_ANNOUNCEMENT_VISIBLE = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "SSN_USERS.user_name, SSN_MESSAGE.messageType, SSN_USERS.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS
			+ " where SSN_MESSAGE.messageType = 'ANNOUNCEMENT' AND SSN_MESSAGE.authorID = SSN_USERS.user_id AND SSN_USERS.accountStatus = 'ACTIVE'"
			+ " order by SSN_MESSAGE.postedAt DESC";
	
	/**
	 * Query to find all chat messages
	 */
//	public static final String FIND_CHAT_MESSAGES = "select message_id, content,"
//			+ "author, messageType, target, postedAt"
//			+ " from "
//			+ SSN_MESSAGE
//			+ " where messageType = 'CHAT'"
//			+ " order by postedAt DESC";
	
	public static final String FIND_CHAT_MESSAGES = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where SSN_MESSAGE.messageType = 'CHAT' AND SSN_MESSAGE.authorID = User1.user_id"
			+ " AND SSN_MESSAGE.targetID = User2.user_id"
			+ " order by SSN_MESSAGE.postedAt DESC";
	
	/**
	 * Query to find all visible chat messages
	 */
//	public static final String FIND_CHAT_MESSAGES = "select message_id, content,"
//			+ "author, messageType, target, postedAt"
//			+ " from "
//			+ SSN_MESSAGE
//			+ " where messageType = 'CHAT'"
//			+ " order by postedAt DESC";
	
	public static final String FIND_CHAT_MESSAGES_VISIBLE = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where SSN_MESSAGE.messageType = 'CHAT' AND SSN_MESSAGE.authorID = User1.user_id"
			+ " AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE'"
			+ " order by SSN_MESSAGE.postedAt DESC";

	/**
	 * Query to find messages between two users
	 */
	public static final String FIND_MESSAGES_BY_TWO_USERS = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where (UPPER(User1.user_name) = UPPER(?) AND UPPER(User2.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id)"
			+ " OR (UPPER(User1.user_name) = UPPER(?) AND UPPER(User2.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id)"
			+ " order by SSN_MESSAGE.postedAt DESC";
	
	/**
	 * Query to find messages between two users
	 */
	public static final String FIND_MESSAGES_BY_TWO_USERS_VISIBLE = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where (UPPER(User1.user_name) = UPPER(?) AND UPPER(User2.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE')"
			+ " OR (UPPER(User1.user_name) = UPPER(?) AND UPPER(User2.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE')"
			+ " order by SSN_MESSAGE.postedAt DESC";

	/**
	 * Query to find chatbuddies by userName
	 */
	public static final String FIND_CHATBUDDIES_BY_USER = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where ((UPPER(User1.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id) "
			+ "OR (UPPER(User2.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id))"
			+ " AND SSN_MESSAGE.postedAt >= ? AND SSN_MESSAGE.postedAt <= ? AND SSN_MESSAGE.messageType = 'CHAT'";
	
	/**
	 * Query to find chatbuddies by userName
	 */
	public static final String FIND_CHATBUDDIES_BY_USER_VISIBLE = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where ((UPPER(User1.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE') "
			+ "OR (UPPER(User2.user_name) = UPPER(?) AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE'))"
			+ " AND SSN_MESSAGE.postedAt >= ? AND SSN_MESSAGE.postedAt <= ? AND SSN_MESSAGE.messageType = 'CHAT'";
	
	/**
	 * Query to find message by ID
	 */
	public static final String FIND_MESSAGE_BY_ID = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where SSN_MESSAGE.message_id = ? AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id";
	
	/**
	 * Query to find message by ID
	 */
	public static final String FIND_MESSAGE_BY_ID_VISIBLE = "select SSN_MESSAGE.message_id, SSN_MESSAGE.content,"
			+ "User1.user_name, SSN_MESSAGE.messageType, User2.user_name, SSN_MESSAGE.postedAt"
			+ " from "
			+ SSN_MESSAGE + ", " + SSN_USERS + " User1, " + SSN_USERS + " User2 "
			+ " where SSN_MESSAGE.message_id = ? AND SSN_MESSAGE.authorID = User1.user_id AND SSN_MESSAGE.targetID = User2.user_id AND User1.accountStatus = 'ACTIVE' AND User2.accountStatus = 'ACTIVE'";

	
	// ****************************************************************
	// All queries related to MEMORY
	// ****************************************************************
	/**
	 * Query to create the MEMORYCRUMB table.
	 */
	public static final String CREATE_MEMORY = "create table IF NOT EXISTS "
			+ SSN_MEMORYCRUMB + " ( memory_id IDENTITY PRIMARY KEY,"
			+ " usedVolatile FLOAT," + " remainingVolatile FLOAT,"
			+ " usedPersistent FLOAT, remainingPersistent FLOAT, createdAt BIGINT)";

	/**
	 * Query to load all memory in the system in 1 hour.
	 */
	public static final String FIND_ALL_MEMORY = "select memory_id, usedVolatile,"
			+ " remainingVolatile , usedPersistent, remainingPersistent, createdAt"
			+ " from (select memory_id, usedVolatile,"
			+ " remainingVolatile , usedPersistent, remainingPersistent, createdAt"
			+ " from " + SSN_MEMORYCRUMB + " order by createdAt desc)"
			+ " where rownum <= 60";

	/**
	 * Query to delete all memory in the system
	 */
	public static final String DELETE_ALL_MEMORY = "delete "
			+ " from " + SSN_MEMORYCRUMB;

	/**
	 * Query to insert a row into the memory table.
	 */
	public static final String INSERT_MEMORY = "insert into " + SSN_MEMORYCRUMB
			+ " (usedVolatile, remainingVolatile, usedPersistent, remainingPersistent, createdAt)"
			+ " values (?, ?, ?, ?, ?)";
	
	// ****************************************************************
	// All queries related to PERFORMANCE
	// ****************************************************************
	/**
	 * Query to create the PERFORMANCE MESSAGE table.
	 */
	public static final String CREATE_PERFORMANCE_MESSAGE = "create table IF NOT EXISTS "
			+ SSN_PERFORMANCE_MESSAGE + " ( message_id IDENTITY PRIMARY KEY,"
			+ " content VARCHAR(512)," + " author VARCHAR(100),"
			+ " messageType VARCHAR(100) , target VARCHAR(100), postedAt BIGINT)";

	/**
	 * Query to insert performance message in the system.
	 */
	public static final String INSERT_PERFORMANCE_MESSAGE = "insert into " + SSN_PERFORMANCE_MESSAGE
			+ " (content, author, messageType, target, postedAt) values (?, ?, ?, ?, ?)";

	/**
	 * Query to find performance messages on wall
	 */
	public static final String FIND_PERFORMANCE_MESSAGES_ON_WALL = "select message_id, content,"
			+ "author, messageType, target, postedAt"
			+ " from "
			+ SSN_PERFORMANCE_MESSAGE
			+ " where messageType = 'WALL'"
			+ " order by postedAt DESC";

	/**
	 * Query to drop PERFORMANCE MESSAGE table.
	 */
	public static final String DROP_PERFORMANCE_MESSAGE = "drop table " + SSN_PERFORMANCE_MESSAGE;
	
	
}
