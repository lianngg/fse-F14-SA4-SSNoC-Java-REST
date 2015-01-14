package edu.cmu.sv.ws.ssnoc.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.po.StatusPO;

/**
 * DAO implementation for saving Status information in the H2 database.
 * 
 */
public class StatusDAOImpl extends BaseDAOImpl implements IStatusDAO {

	private List<StatusPO> processResults(PreparedStatement stmt) {
		Log.enter(stmt);

		if (stmt == null) {
			Log.warn("Inside processResults method with NULL statement object.");
			return null;
		}

		Log.debug("Executing stmt = " + stmt);
		List<StatusPO> statuses = new ArrayList<StatusPO>();
		try (ResultSet rs = stmt.executeQuery()) {
			//DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			while (rs.next()) {
				StatusPO po = new StatusPO();
				po = new StatusPO();
				po.setCrumbID(rs.getLong(1));
				po.setUserName(rs.getString(2));
				po.setStatusCode(rs.getString(3));
				po.setCreateAt(rs.getLong(4));

				statuses.add(po);
			}
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit(statuses);
		}

		return statuses;
	}

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
	@Override
	public List<StatusPO> findByName(String userName) {
		Log.enter(userName);

		if (userName == null) {
			Log.warn("Inside findByName method with NULL userName.");
			return null;
		}

		List<StatusPO> statuses = new ArrayList<StatusPO>();
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_STATUSCRUMB_BY_NAME)) {
			stmt.setString(1, userName.toUpperCase());

			statuses = processResults(stmt);
		} catch (SQLException e) {
			handleException(e);
			Log.exit(statuses);
		}

		return statuses;
	}

	/**
	 * This method will save the information of the user into the database.
	 * 
	 * @param statusPO
	 *            - Status information to be saved.
	 */
	@Override
	public void save(StatusPO statusPO) {
		Log.enter(statusPO);
		if (statusPO == null) {
			Log.warn("Inside save method with statusPO == NULL");
			return;
		}

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQL.INSERT_STATUSCRUMB)) {
			stmt.setString(1, statusPO.getUserName());
			stmt.setString(2, statusPO.getStatusCode());
			stmt.setLong(3, statusPO.getCreateAt());

			int rowCount = stmt.executeUpdate();
			Log.trace("Statement executed, and " + rowCount + " rows inserted.");
		} catch (SQLException e) {
			handleException(e);
		} finally {
			Log.exit();
		}
	}
	
	/**
	 * This method with search for a list of status by its crumbID in the database.
	 * 
	 * @param scrumbId
	 *            - ID to search for.
	 * 
	 * @return - StatusPO with the user information if a match is found.
	 */
	@Override
	public	StatusPO findByID(long crumbID) {
		Log.enter(crumbID);

//		if (userName == null) {
//			Log.warn("Inside findByName method with NULL userName.");
//			return null;
//		}

		List<StatusPO> statuses = new ArrayList<StatusPO>();
		StatusPO po = null;
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn
						.prepareStatement(SQL.FIND_STATUSCRUMB_BY_ID)) {
			stmt.setLong(1, crumbID);

			statuses = processResults(stmt);
			if (statuses.size() != 1) {
				Log.debug("get findByID num more than 1");
			}
			else {
				po = statuses.get(0);
			}
		} catch (SQLException e) {
			handleException(e);
			Log.exit(po);
		}

		return po;
	}
}
