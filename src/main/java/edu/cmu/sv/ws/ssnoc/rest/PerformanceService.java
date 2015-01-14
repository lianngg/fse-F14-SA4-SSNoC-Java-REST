package edu.cmu.sv.ws.ssnoc.rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cmu.sv.ws.ssnoc.common.exceptions.ServiceException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.dto.PerformanceData;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to users.
 * 
 */

@Path("/performance")
public class PerformanceService extends BaseService {
	private static MeasurePerformanceThread thread = null;
	

	public MeasurePerformanceThread getThread () {
		if (thread == null) {
			thread = new MeasurePerformanceThread();
		}
		return thread;
	}
	
	/**
	 * This method starts measuring performance
	 * 
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/setup")
	public Response measurePerformanceSetup() {
		Log.enter();
		
		SystemStateUtils.startMeasurePerformance();
		try {
			DBUtils.dropPerformanceMessageTablesInDB();
			DBUtils.createPerformanceMessageTablesInDB();
			getThread().start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.debug("error in measurePerformanceSetup");
		}

		Log.exit();
		return ok();
	}

	/**
	 * This method ends measuring performance
	 * 
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/teardown")
	public Response measurePerformanceTearDown() {
		Log.enter();
		
		try {
			Thread.sleep(5000);
			SystemStateUtils.endMeasurePerformance();
			DBUtils.dropPerformanceMessageTablesInDB();
			getThread().stop();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.debug("error in measurePerformanceTearDown");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.exit();

		return ok();
	}

	
	/**
	 * This method loads all active users in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/data")
	public PerformanceData getPerformanceData() {
		Log.enter();

		PerformanceData data = new PerformanceData();
		data.setGetPerSecond(SystemStateUtils.getGetPerSecond());
		data.setPostPerSecond(SystemStateUtils.getPostPerSecond());
		Log.exit(data);

		return data;
	}
}
