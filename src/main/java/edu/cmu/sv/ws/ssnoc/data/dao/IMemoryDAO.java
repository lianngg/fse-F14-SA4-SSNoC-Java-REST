package edu.cmu.sv.ws.ssnoc.data.dao;

import java.util.List;

import edu.cmu.sv.ws.ssnoc.data.po.MemoryPO;


/**
 * Interface specifying the contract that all implementations will implement to
 * provide persistence of User information in the system.
 * 
 */
public interface IMemoryDAO {
	/**
	 * This method will save the information of the memory into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	void save(MemoryPO memoryPO);

	/**
	 * This method will load all the memory in the
	 * database.
	 * 
	 * @return - List of all memory in 1 hour.
	 */
	List<MemoryPO> loadMemory();

	/**
	 * This method will delete all information of the memory in the database.
	 * 
	 */
	void deleteMemory();
}
