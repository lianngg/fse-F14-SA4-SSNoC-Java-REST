package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.dao.IUserDAO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

@Path("/users")
public class UsersService extends BaseService {
	/**
	 * This method loads all active users in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@XmlElementWrapper(name = "users")
	public List<User> loadUsers() {
		Log.enter();

		List<User> users = null;
		try {
			List<UserPO> userPOs = DAOFactory.getInstance().getUserDAO().loadUsers();

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
	 * This method loads all active users in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/users/active")
	public List<User> loadUsersVisible() {
		Log.enter();

		List<User> users = null;
		try {
			List<UserPO> userPOs = DAOFactory.getInstance().getUserDAO().loadUsersVisible();

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
	 * This method retrieves all users with whom a user chat with.
	 * 
	 * @return - List of users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{userName}/chatbuddies")
	public List<User> loadChatbuddies(@PathParam("userName") String userName) {
		Log.enter();

		List<User> users = null;
		try {
			IMessageDAO messageDAO= DAOFactory.getInstance().getMessageDAO();
			IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();
			List<String> messagePOs = messageDAO.findChatbuddiesByUser(userName, 0, new Date().getTime() / 1000);

			users = new ArrayList<User>();
			for (String name : messagePOs) {
				UserPO userPO = userDAO.findByName(name);
				User dto = ConverterUtils.convert(userPO);
				users.add(dto);
			}
		} catch (Exception e) {
			handleException(e);
		} finally {
			Log.exit(users);
		}

		return users;
	}
}
