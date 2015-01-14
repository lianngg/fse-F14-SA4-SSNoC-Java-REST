package edu.cmu.sv.ws.ssnoc.rest;

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
import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.dto.Message;

/**
 * This class contains the implementation of the RESTful API calls made with
 * respect to users.
 * 
 */

@Path("/message")
public class MessageService extends BaseService {
	/**
	 * This method posts a message on public wall
	 * 
	 * @param userName
	 *            - Name of a user
	 * @param message
	 *            - message related data
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}")
	public Response addMessageOnPublicWall(@PathParam("userName") String userName,
			Message message) {
		Log.enter(userName, message);
		MessagePO po = null;
		
		try {
			
			
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();

			po = ConverterUtils.convert(message);
			po.setMessageType("WALL");
			po.setAuthor(userName);
			po.setTarget(userName);
			dao.save(po, SystemStateUtils.getSystemState());
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(po);
	}

	/**
	 * Send private message from sendingUserName to receivingUserName
	 * 
	 * @param sendingUserName
	 *            - name of sending user
	 * @param receivingUserName
	 *            - name of receiving user
	 * 
	 * @return - Status 200 when successful login. Else other status.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{sendingUserName}/{receivingUserName}")
	public Response addMessageAsChat(@PathParam("sendingUserName") String sendingUserName,
			@PathParam("receivingUserName") String receivingUserName,
			Message message) {
		Log.enter(sendingUserName, receivingUserName, message);
		MessagePO po = null;
		
		try {
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();
			
			po = ConverterUtils.convert(message);
			po.setMessageType("CHAT");
			po.setAuthor(sendingUserName);
			po.setTarget(receivingUserName);
			dao.save(po, SystemStateUtils.getSystemState());
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(po);
	}

	/**
	 * All message information related to a particular ID.
	 * 
	 * @param messageID
	 *            - message ID
	 * 
	 * @return - Details of the message
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{messageID}")
	public Message getMessageByID(@PathParam("messageID") long messageID) {
		Log.enter(messageID);

		Message message = null;
		try {
			IMessageDAO messageDao = DAOFactory.getInstance().getMessageDAO();
			MessagePO po = messageDao.findMessageByID(messageID);

			if (po != null) {
				Log.trace("Message exists.");
				message = ConverterUtils.convert(po);
			}
			else {
				Log.trace("No such ID!!");
			}

		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return message;
	}

	/**
	 * This method posts a message on public wall
	 * 
	 * @param userName
	 *            - Name of a user
	 * @param message
	 *            - message related data
	 * @return - An object of type Response with the status of the request
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/announcement/{userName}")
	public Response addMessageAsAnnouncement(@PathParam("userName") String userName,
			Message message) {
		Log.enter(userName, message);
		MessagePO po = null;
		
		try {
			IMessageDAO dao = DAOFactory.getInstance().getMessageDAO();

			po = ConverterUtils.convert(message);
			po.setMessageType("ANNOUNCEMENT");
			po.setAuthor(userName);
			po.setTarget(userName);
			dao.save(po, SystemStateUtils.getSystemState());
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit();
		}

		return created(po);
	}
}
