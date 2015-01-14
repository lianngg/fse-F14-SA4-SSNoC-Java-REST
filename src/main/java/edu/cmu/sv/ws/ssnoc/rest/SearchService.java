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
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;

@Path("/search")
public class SearchService extends BaseService {
	/**
	 * This method search given userName in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/userName/{userName}")
	public List<User> searchUserName(@PathParam("userName") String userName) {
		Log.enter(userName);

		List<User> users = null;
		try {
			List<UserPO> userPOs = DAOFactory.getInstance().getSearchDao().searchUserName(userName);

			users = new ArrayList<User>();
			for (UserPO po : userPOs) {
				User dto = ConverterUtils.convert(po);
				users.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}
	
	/**
	 * This method search given status in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/status/{status}")
	public List<User> searchStatus(@PathParam("status") String status) {
		Log.enter(status);

		List<User> users = null;
		try {
			List<UserPO> userPOs = DAOFactory.getInstance().getSearchDao().searchStatus(status);

			users = new ArrayList<User>();
			for (UserPO po : userPOs) {
				User dto = ConverterUtils.convert(po);
				users.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}
	
	/**
	 * This method search given announcement in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/announcement/{announcement}")
	public List<Message> searchAnnouncement(@PathParam("announcement") String announcement) {
		Log.enter(announcement);

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getSearchDao().searchAnnouncement(announcement);

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
	 * This method search given public wall messages in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/wall/{wall}")
	public List<Message> searchMessageOnPublicWall(@PathParam("wall") String wall) {
		Log.enter(wall);

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getSearchDao().searchMessageOnPublicWall(wall);

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
	 * This method search given chat in the system.
	 * 
	 * @return list of messages.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/chat/{chat}")
	public List<Message> searchMessageAsChat(@PathParam("chat") String chat) {
		Log.enter(chat);

		List<Message> messages = null;
		try {
			List<MessagePO> messagePOs = DAOFactory.getInstance().getSearchDao().searchMessageAsChat(chat);

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
