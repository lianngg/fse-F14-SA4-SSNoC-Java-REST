package edu.cmu.sv.ws.ssnoc.data.dao;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;

/**
 * Singleton Factory pattern class to fetch all DAO implementations.
 */
public final class DAOFactory {
	
	private DAOFactory() {
		this.userDao = new UserDAOImpl();
		this.statusDao = new StatusDAOImpl();
		this.messageDao = new MessageDAOImpl();
		this.memoryDao = new MemoryDAOImpl();
		this.searchDao = new SearchDAOImpl();
	}
	
	private static DAOFactory instance;

	/**
	 * Singleton instance access method to get the instance of the class to
	 * request a specific DAO implementation.
	 * 
	 * @return - DAOFactory instance
	 */
	public static final DAOFactory getInstance() {
		if (instance == null) {
			Log.info("Creating a new DAOFactory singleton instance.");
			instance = new DAOFactory();
		}

		return instance;
	}
	
	private IUserDAO userDao;
	private IStatusDAO statusDao;
	private IMessageDAO messageDao;
	private IMemoryDAO memoryDao;
	private ISearchDAO searchDao;

	/**
	 * Method to get a new object implementing IUserDAO
	 * 
	 * @return - Object implementing IUserDAO
	 */
	public IUserDAO getUserDAO() {
		return this.userDao; 
	}

	/**
	 * Method to get a new object implementing IStatusDAO
	 * 
	 * @return - Object implementing IUserDAO
	 */
	public IStatusDAO getStatusDAO() {
		return this.statusDao;
	}
	
	/**
	 * Method to get a new object implementing IMessageDAO
	 * 
	 * @return - Object implementing IMessageDAO
	 */
	public IMessageDAO getMessageDAO() {
		return this.messageDao;
	}
	
	/**
	 * Method to get a new object implementing IMemoryDAO
	 * 
	 * @return - Object implementing IMemoryDAO
	 */
	public IMemoryDAO getMemoryDAO() {
		return this.memoryDao;
	}

	/**
	 * Method to get a new object implementing ISearchDAO
	 * 
	 * @return - Object implementing ISearchDAO
	 */
	public ISearchDAO getSearchDao() {
		return this.searchDao;
	}
}
