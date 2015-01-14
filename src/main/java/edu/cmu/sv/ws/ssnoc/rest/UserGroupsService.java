package edu.cmu.sv.ws.ssnoc.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.User;

@Path("/usergroups")
public class UserGroupsService extends BaseService {
//	/**
//	 * This method loads all active users in the system.
//	 * 
//	 * @return - List of all active users.
//	 */
//	@GET
//	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//	@Path("/unconnected")
//	public List<List<String>> loadUserGroups() {
//		Log.enter();
//
//		List<List<String>> result = new ArrayList<List<String>>();
//		List<String> names = new ArrayList<String>();
//		//List<Message> messages = new ArrayList<Message>();
//		try {
//			List<UserPO> userPOs = DAOFactory.getInstance().getUserDAO().loadUsers();
//			for (UserPO po : userPOs) {
//				User dto = ConverterUtils.convert(po);
//				names.add(dto.getUserName());
//			}
//			
//			/*List<MessagePO> messagePOs = DAOFactory.getInstance().getMessageDAO().findChatMessages();
//			for (MessagePO po : messagePOs) {
//				Message dto = ConverterUtils.convert(po);
//				messages.add(dto);
//			}*/
//		} catch (Exception e) {
//			handleException(e);
//		} finally {
//		}
//		
//		if (names.size() == 0) {
//			//return result;
//		}
//		for (String name : names) {
//			if (result.size() == 0) {
//				List<String> only = new ArrayList<String>();
//				only.add(name);
//				result.add(only);
//			}
//			else {
//				List<String> chatBuddies = DAOFactory.getInstance().getMessageDAO().findChatbuddiesByUser(name);
//				boolean insert = false;
//				for (List<String> cluster : result) {
//					if (chatBuddies.size() == 0) {
//						cluster.add(name);
//						insert = true;
//					}
//					else {
//						boolean find = false;
//						for (String buddy : chatBuddies) {
//							if (cluster.contains(buddy)) {
//								find = true;
//								break;
//							}
//						}
//						if (find == false) {
//							cluster.add(name);
//							insert = true;
//						}
//					}
//				}
//				if (insert == false) {
//					List<String> only = new ArrayList<String>();
//					only.add(name);
//					result.add(only);
//				}
//			}
//		}
//		Log.exit(result);
//		return result;
//	}
	
//	@XmlRootElement
	public static class UserGroup{
	    private List<String> users;

	    public List<String> getUser() {
	        return users;
	    }

	    public void setUser(List<String> users) {
	        this.users = users;
	    }
	    
	    @Override
	    public String toString() {
	        return new Gson().toJson(this);
	    }

	    public static UserGroup build(Set<String> t) {
	    	UserGroup stringList = new UserGroup();

	        List<String> users = new ArrayList<String>();      
	        for (String product : t) {
	            users.add(product);
	        }

	        stringList.setUser(users);

	        return stringList;
	    }
	}
	/**
	 * This method loads all active users in the system.
	 * 
	 * @return - List of all active users.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/unconnected/{timeWindowInMinutes}")
//	@XmlElementWrapper(name = "UserGroups")
	public List<UserGroup> loadUserGroups(@PathParam("timeWindowInMinutes") long timeWindowInMinutes) {
		Log.enter();

		Set<Set<String>> result = new HashSet<Set<String>>();
		List<String> names = new ArrayList<String>();
		//List<Message> messages = new ArrayList<Message>();
		try {
			List<UserPO> userPOs = DAOFactory.getInstance().getUserDAO().loadUsers();
			for (UserPO po : userPOs) {
				User dto = ConverterUtils.convert(po);
				names.add(dto.getUserName());
			}
		} catch (Exception e) { 
			handleException(e);
		} finally {
		}
		
		if (names.size() == 0) {
			return new ArrayList<UserGroup>();
		}
//		Set<String> resultElement = new HashSet<String>();
//		for (String name : names) {
//			resultElement.add(name);
//		}
//		result.add(resultElement);
//		for (String name : names) {
//			List<String> chatBuddies = DAOFactory.getInstance().getMessageDAO().findChatbuddiesByUser(name, new Date().getTime() - timeWindowInMinutes * 60 * 1000, new Date().getTime());
//			Set<String> chatBuddiesSet = new HashSet<String>(chatBuddies);
//			for (String buddy : chatBuddiesSet) {
//				Set<Set<String>> newResult = new HashSet<Set<String>>(result);
//				for (Set<String> element : result) {
//					if (element.contains(name) && element.contains(buddy)) {
//						if (element.size() == 2) {
//							newResult.remove(element);
//							continue;
//						}
//						Set<String> element1 = new HashSet<String>(element);
//						Set<String> element2 = new HashSet<String>(element);
//						newResult.remove(element);
//						element1.remove(name);
//						element2.remove(buddy);
//						newResult.add(element1);
//						newResult.add(element2);
//					}
//				}
//				result = newResult;
//			}
//		}
//		for (String name : names) {
//			boolean find = false;
//			for (Set<String> element : result) {
//				if (element.contains(name)) {
//					find = true;
//					break;
//				}
//			}
//			if (!find) {
//				Set<String> only = new HashSet<String>();
//				only.add(name);
//				result.add(only);
//			}
//		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String name : names) {
			List<String> chatBuddies = DAOFactory.getInstance().getMessageDAO().findChatbuddiesByUser(name, new Date().getTime() / 1000 - timeWindowInMinutes * 60, new Date().getTime() / 1000);
			map.put(name, chatBuddies);
		}
		result = loadUserGroupsMethod(names, map);
		
		
		List<UserGroup> ok = new ArrayList<UserGroup>();
		for (Set<String> t : result) {
			ok.add(UserGroup.build(t));
		}
		Log.exit(ok);
		return ok;
	}
	
	public Set<Set<String>> loadUserGroupsMethod (List<String> names, Map<String, List<String>> map) {
		Log.enter(names, map);
		Set<Set<String>> result = new HashSet<Set<String>>();
		Set<String> resultElement = new HashSet<String>();
		for (String name : names) {
			resultElement.add(name);
		}
		result.add(resultElement);
		for (String name : names) {
			List<String> chatBuddies = map.get(name);
			Set<String> chatBuddiesSet = new HashSet<String>(chatBuddies);
			for (String buddy : chatBuddiesSet) {
				Set<Set<String>> newResult = new HashSet<Set<String>>(result);
				for (Set<String> element : result) {
					if (element.contains(name) && element.contains(buddy)) {
						if (element.size() == 2) {
							newResult.remove(element);
							continue;
						}
						Set<String> element1 = new HashSet<String>(element);
						Set<String> element2 = new HashSet<String>(element);
						newResult.remove(element);
						element1.remove(name);
						element2.remove(buddy);
						newResult.add(element1);
						newResult.add(element2);
					}
				}
				result = newResult;
			}
		}
		for (String name : names) {
			boolean find = false;
			for (Set<String> element : result) {
				if (element.contains(name)) {
					find = true;
					break;
				}
			}
			if (!find) {
				Set<String> only = new HashSet<String>();
				only.add(name);
				result.add(only);
			}
		}
		
		Set<Set<String>> r = new HashSet<Set<String>>();
		for (Set<String> first : result) {
			boolean find = false;
			for (Set<String> second : result) {
				if (second.containsAll(first) && first.containsAll(second)){ 
					continue;
				}
				else if (second.containsAll(first)){
					find = true;
					break;
				}
			}
			if (!find) {
				r.add(first);
			}
		}
		result = r;
		Log.exit(result);
		return result;
	}
}
