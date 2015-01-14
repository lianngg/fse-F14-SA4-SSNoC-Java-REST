package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import edu.cmu.sv.ws.ssnoc.common.exceptions.ServiceException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMemoryDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryPO;
import edu.cmu.sv.ws.ssnoc.dto.Memory;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to users.
 * 
 */

@Path("/memory")
public class MemoryService extends BaseService {
	private static MeasureMemoryThread thread = null;
	

	public MeasureMemoryThread getThread () {
		if (thread == null) {
			thread = new MeasureMemoryThread();
		}
		return thread;
	}
	
	
	/**
	 * This method start memory measurement
	 * 
	 * @return - Status 200 when successfully starting. Else other status.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/start")
	public Response startMeasureMemory() {
		Log.enter();

		getThread().start();
		Log.exit();

		return ok();
	}

	/**
	 * This method is used to stop memory measurement
	 * 
	 * @return - Status 200 when successful stopping. Else other status.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/stop")
	public Response stopMeasureMemory() {
		Log.enter();
		
		getThread().stop();
		Log.exit();

		return ok();
	}

	/**
	 * Delete all memory information.
	 * 
	 * @return - Status 200 when successful stopping. Else other status.
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/")
	public Response deleteMemory() {
		Log.enter();

		try {
			IMemoryDAO dao = DAOFactory.getInstance().getMemoryDAO();
			dao.deleteMemory();
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return ok();
	}
	
	/**
	 * Get all memory information in 1 hour.
	 * 
	 * @return - List of memory information
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	//@Path("/")
	public List<Memory> loadMemory() {
		Log.enter();

		List<Memory> memorys = null;
		try {
			List<MemoryPO> memoryPOs = DAOFactory.getInstance().getMemoryDAO().loadMemory();

			memorys = new ArrayList<Memory>();
			for (MemoryPO po : memoryPOs) {
				Memory dto = ConverterUtils.convert(po);
				memorys.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(memorys);
		}

		return memorys;
	}
	
//	/**
//	 * Get all memory information in specific hours.
//	 * 
//	 * @return - List of memory information
//	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	@Path("/interval/{timeWindowInHours}")
//	public List<Memory> loadMemoryFroSpecificTimeInterval(@PathParam("timeWindowInHours") String timeWindowInHours) {
//		Log.enter(timeWindowInHours);
//
//		List<Memory> memorys = null;
//		try {
//			List<MemoryPO> memoryPOs = DAOFactory.getInstance().getMemoryDAO().loadMemory();
//
//			memorys = new ArrayList<Memory>();
//			for (MemoryPO po : memoryPOs) {
//				Memory dto = ConverterUtils.convert(po);
//				memorys.add(dto);
//			}
//		} catch (Exception e) {
//			handleException(e);
//		} finally {
//			Log.exit(memorys);
//		}
//
//		return memorys;
//	}
}