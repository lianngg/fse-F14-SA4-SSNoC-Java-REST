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
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;

@Path("/messages")
public class MessagesService extends BaseService {
	/**
	 * This method loads all messages on the public wall.
	 * 
	 * @return - List of messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/wall")
	public List<Message> loadMessagesOnWall() {
		Log.enter();

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().findMessagesOnWall();

			messages = new ArrayList<Message>();
			for (MessagePO po : messagePOs) {
				Message dto = ConverterUtils.convert(po);
				messages.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method loads all visible messages on the public wall.
	 * 
	 * @return - List of messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/wall/visible")
	public List<Message> loadMessagesOnWallVisible() {
		Log.enter();

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().findMessagesOnWallVisible();

			messages = new ArrayList<Message>();
			for (MessagePO po : messagePOs) {
				Message dto = ConverterUtils.convert(po);
				messages.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method loads all messages as announcement.
	 * 
	 * @return - List of messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/announcement")
	public List<Message> loadMessagesAsAnnouncement() {
		Log.enter();

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().findMessagesAsAnnouncement();

			messages = new ArrayList<Message>();
			for (MessagePO po : messagePOs) {
				Message dto = ConverterUtils.convert(po);
				messages.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method loads all messages as announcement.
	 * 
	 * @return - List of messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/announcement/visible")
	public List<Message> loadMessagesAsAnnouncementVisible() {
		Log.enter();

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().findMessagesAsAnnouncementVisible();

			messages = new ArrayList<Message>();
			for (MessagePO po : messagePOs) {
				Message dto = ConverterUtils.convert(po);
				messages.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method loads messages between two people
	 * 
	 * @return - List of messages
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName1}/{userName2}")
	public List<Message> loadMessagesBetweenTwoUsers(@PathParam("userName1") String userName1,
			@PathParam("userName2") String userName2) {
		Log.enter(userName1, userName2);
		Log.debug(userName1);

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().findMessagesBetweenTwoUsers(userName1, userName2);

			messages = new ArrayList<Message>();
			for (MessagePO po : messagePOs) {
				Message dto = ConverterUtils.convert(po);
				messages.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
	
	/**
	 * This method loads messages between two people
	 * 
	 * @return - List of messages
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName1}/{userName2}/visible")
	public List<Message> loadMessagesBetweenTwoUsersVisible(@PathParam("userName1") String userName1,
			@PathParam("userName2") String userName2) {
		Log.enter(userName1, userName2);
		Log.debug(userName1);

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().findMessagesBetweenTwoUsersVisible(userName1, userName2);

			messages = new ArrayList<Message>();
			for (MessagePO po : messagePOs) {
				Message dto = ConverterUtils.convert(po);
				messages.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(messages);
		}

		return messages;
	}
}
