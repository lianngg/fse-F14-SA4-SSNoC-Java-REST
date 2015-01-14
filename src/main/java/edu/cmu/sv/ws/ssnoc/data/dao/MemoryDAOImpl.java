package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryPO;

/**
 * DAO implementation for saving User information in the H2 database.
 * 
 */
public class MemoryDAOImpl extends BaseDAOImpl implements IMemoryDAO {
	/**
	 * This method will save the information of the memory into the database.
	 * 
	 * @param userPO
	 *            - User information to be saved.
	 */
	public void save(MemoryPO memoryPO) {
		Log.enter(memoryPO);
		if (memoryPO == null) {
			Log.warn("Inside save method with memoryPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_MEMORY)) {
			stmt.setLong(1, memoryPO.getUsedVolatile());
			stmt.setLong(2, memoryPO.getRemainingVolatile());
			stmt.setLong(3, memoryPO.getUsedPersistent());
			stmt.setLong(4, memoryPO.getRemainingPersistent());
	        stmt.setLong(5, memoryPO.getCreatedTimeStamp());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}

	/**
	 * This method will load all the memory in the
	 * database.
	 * 
	 * @return - List of all memory in 1 hour.
	 */
	public List<MemoryPO> loadMemory() {
		Log.enter();

		String query = SQL.FIND_ALL_MEMORY;

		List<MemoryPO> memory = new ArrayList<MemoryPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			memory = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(memory);
		}

		return memory;
	}

	/**
	 * This method will delete all information of the memory in the database.
	 * 
	 */
	public void deleteMemory() {
		Log.enter();

		String query = SQL.DELETE_ALL_MEMORY;

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			
			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
			Log.exit();
		}

		return;
	}
	
	private List<MemoryPO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<MemoryPO> memory = new ArrayList<MemoryPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				MemoryPO po = new MemoryPO();
				po.setCrumbID(rs.getLong(1));
				po.setUsedVolatile(rs.getLong(2));
				po.setRemainingVolatile(rs.getLong(3));
				po.setUsedPersistent(rs.getLong(4));
				po.setRemainingPersistent(rs.getLong(5));
				po.setCreatedTimeStamp(rs.getLong(6));

				memory.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(memory);
		}

		return memory;
	}
}
