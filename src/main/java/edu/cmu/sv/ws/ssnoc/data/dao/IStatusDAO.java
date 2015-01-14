package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.StatusPO;

/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of Status information in the system.
 * 
 */
public interface IStatusDAO {
	/**
	 * This method will save the information of the status into the database.
	 * 
	 * @param StatusPO
	 *            - User information to be saved.
	 */
	void save(StatusPO statusPO);

	/**
	 * This method with search for a list of status by his userName in the database. The
	 * search performed is a case insensitive search to allow case mismatch
	 * situations.
	 * 
	 * @param userName
	 *            - User name to search for.
	 * 
	 * @return - StatusPO with the user information if a match is found.
	 */
	List<StatusPO> findByName(String userName);

	/**
	 * This method with search for a list of status by its crumbID in the database.
	 * 
	 * @param scrumbId
	 *            - ID to search for.
	 * 
	 * @return - StatusPO with the user information if a match is found.
	 */
	StatusPO findByID(long crumbID);
}
