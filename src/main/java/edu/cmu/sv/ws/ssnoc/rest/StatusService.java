package edu.cmu.sv.ws.ssnoc.rest;

import java.util.Date;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IStatusDAO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.data.po.StatusPO;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.dto.Status;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to status.
 * 
 */

@Path("/status")
public class StatusService extends BaseService {
	
    /**
	 * This method updates status and adds
	 * it to the database
	 * 
	 * @param user
	 *            - updatedAt, statusCode, Location (optional)
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public Response update(@PathParam("userName") String userName,
			Status status) {
		Log.enter(userName, status);
		User resp = new User();
		status.setUserName(userName);

		StatusPO statusPO = null;
		try {
			IUserDAO dao = DAOFactory.getInstance().getUserDAO();
			IStatusDAO statusDao = DAOFactory.getInstance().getStatusDAO();
			UserPO existingUser = dao.findByName(userName);

			// Validation to check that user name should be unique
			// in the system. If a new users tries to register with
			// an existing userName, notify that to the user.
			if (existingUser != null) {
				statusPO = ConverterUtils.convert(status);
				Log.trace("User name provided already exists. Validating if it is same password ...");
				existingUser.setLastStatusCode(status.getStatusCode());
				if (status.getCreateAt() == 0) {
				    existingUser.setModifiedTimeStamp(new Date().getTime() / 1000);
				} else {
				    existingUser.setModifiedTimeStamp(status.getCreateAt());
				}				
				dao.updateStatus(existingUser);
				statusDao.save(statusPO);
			}
			else {
				Log.trace("No such user!!");
			}

			resp = ConverterUtils.convert(existingUser);
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(resp);
	}

	/**
	 * All status information related to a particular ID.
	 * 
	 * @param crumbID
	 *            - statuscrumb ID
	 * 
	 * @return - Details of the status
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{crumbID}")
	public Status getStatusByID(@PathParam("crumbID") long crumbID) {
		Log.enter(crumbID);

		Status status = null;
		try {
			IStatusDAO statusDao = DAOFactory.getInstance().getStatusDAO();
			StatusPO po = statusDao.findByID(crumbID);

			if (po != null) {
				Log.trace("Statuscrumb exists.");
				status = ConverterUtils.convert(po);
			}
			else {
				Log.trace("No such ID!!");
			}

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return status;
	}
}
