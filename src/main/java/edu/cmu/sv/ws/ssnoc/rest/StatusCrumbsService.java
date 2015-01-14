package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.StatusPO;
import edu.cmu.sv.ws.ssnoc.dto.Status;

@Path("/statuscrumbs")
public class StatusCrumbsService extends BaseService {
	/**
	 * This method loads all statuscrumbs related to given users in the system.
	 * 
	 * @return - List of all statuscrumbs.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public List<Status> loadStatuses(@PathParam("userName") String userName) {
		Log.enter();

		List<Status> statuses = null;
		try {
			List<StatusPO> statusPOs = DAOFactory.getInstance().getStatusDAO().findByName(userName);

			statuses = new ArrayList<Status>();
			for (StatusPO po : statusPOs) {
				Status dto = ConverterUtils.convert(po);
				statuses.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(statuses);
		}

		return statuses;
	}
}
