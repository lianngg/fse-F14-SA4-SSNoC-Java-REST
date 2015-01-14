package edu.cmu.sv.ws.ssnoc.common.utils;

import edu.cmu.sv.ws.ssnoc.data.po.MemoryPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.dto.Memory;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.data.po.StatusPO;
import edu.cmu.sv.ws.ssnoc.dto.Status;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.dto.Message;

/**
 * This is a utility class used to convert PO (Persistent Objects) and View
 * Objects into DTO (Data Transfer Objects) objects, and vice versa. <br/>
 * Rather than having the conversion code in all classes in the rest package,
 * they are maintained here for code re-usability and modularity.
 * 
 */
public class ConverterUtils {
	/**
	 * Convert UserPO to User DTO object.
	 * 
	 * @param po
	 *            - User PO object
	 * 
	 * @return - User DTO Object
	 */
	public static final User convert(UserPO po) {
		if (po == null) {
			return null;
		}

		User dto = new User();
		dto.setUserName(po.getUserName());
		dto.setPassword(po.getPassword());
		dto.setStatus(po.getLastStatusCode());
		dto.setCreatedAt(po.getCreatedTimeStamp());
		dto.setModifiedAt(po.getModifiedTimeStamp());
		dto.setPrivilege(po.getPrivilege());
		dto.setAccountStatus(po.getAccountStatus());

		return dto;
	}

	/**
	 * Convert User DTO to UserPO object
	 * 
	 * @param dto
	 *            - User DTO object
	 * 
	 * @return - UserPO object
	 */
	public static final UserPO convert(User dto) {
		if (dto == null) {
			return null;
		}

		UserPO po = new UserPO();
		po.setUserName(dto.getUserName());
		po.setPassword(dto.getPassword());
		po.setCreatedTimeStamp(dto.getCreatedAt());
		po.setLastStatusCode(dto.getStatus());
		po.setModifiedTimeStamp(dto.getModifiedAt());
		po.setPrivilege(dto.getPrivilege());
		po.setAccountStatus(dto.getAccountStatus());

		return po;
	}
	
	/**
	 * Convert StatusPO to Status DTO object.
	 * 
	 * @param po
	 *            - Status PO object
	 * 
	 * @return - Status DTO Object
	 */
	public static final Status convert(StatusPO po) {
		if (po == null) {
			return null;
		}

		Status dto = new Status();
		dto.setCrumbID(po.getCrumbID());
		dto.setUserName(po.getUserName());
		dto.setStatusCode(po.getStatusCode());
		dto.setCreateAt(po.getCreateAt());

		return dto;
	}

	/**
	 * Convert Status DTO to StatusPO object
	 * 
	 * @param dto
	 *            - Status DTO object
	 * 
	 * @return - StautsPO object
	 */
	public static final StatusPO convert(Status dto) {
		if (dto == null) {
			return null;
		}

		StatusPO po = new StatusPO();
		po.setUserName(dto.getUserName());
		po.setCreateAt(dto.getCreateAt());
		po.setStatusCode(dto.getStatusCode());

		return po;
	}
	
	/**
	 * Convert MessagePO to Message DTO object.
	 * 
	 * @param po
	 *            - Message PO object
	 * 
	 * @return - Message DTO Object
	 */
	public static final Message convert(MessagePO po) {
		if (po == null) {
			return null;
		}

		Message dto = new Message();
		dto.setMessageId(po.getMessageId());
		dto.setAuthor(po.getAuthor());
		dto.setContent(po.getContent());
		dto.setMessageType(po.getMessageType());
		dto.setPostedAt(po.getPostedAt());
		dto.setTarget(po.getTarget());

		return dto;
	}
	
	/**
	 * Convert Message DTO to MessagePO object
	 * 
	 * @param dto
	 *            - Message DTO object
	 * 
	 * @return - MessagePO object
	 */
	public static final MessagePO convert(Message dto) {
		if (dto == null) {
			return null;
		}

		MessagePO po = new MessagePO();
		po.setMessageId(dto.getMessageId());
		po.setAuthor(dto.getAuthor());
		po.setContent(dto.getContent());
		po.setMessageType(dto.getMessageType());
		po.setPostedAt(dto.getPostedAt());
		po.setTarget(dto.getTarget());

		return po;
	}
	
	/**
	 * Convert MemoryPO to Memory DTO object.
	 * 
	 * @param po
	 *            - Memory PO object
	 * 
	 * @return - Memory DTO Object
	 */
	public static final Memory convert(MemoryPO po) {
		if (po == null) {
			return null;
		}

		Memory dto = new Memory();
		dto.setCrumbID(po.getCrumbID());
		dto.setCreatedTimeStamp(po.getCreatedTimeStamp());
		dto.setRemainingPersistent(po.getRemainingPersistent());
		dto.setRemainingVolatile(po.getRemainingVolatile());
		dto.setUsedPersistent(po.getUsedPersistent());
		dto.setUsedVolatile(po.getUsedVolatile());

		return dto;
	}
	
	/**
	 * Convert Memory DTO to MemoryPO object
	 * 
	 * @param dto
	 *            - Memory DTO object
	 * 
	 * @return - MemoryPO object
	 */
	public static final MemoryPO convert(Memory dto) {
		if (dto == null) {
			return null;
		}

		MemoryPO po = new MemoryPO();
		po.setCrumbID(dto.getCrumbID());
		po.setCreatedTimeStamp(dto.getCreatedTimeStamp());
		po.setRemainingPersistent(dto.getRemainingPersistent());
		po.setRemainingVolatile(dto.getRemainingVolatile());
		po.setUsedPersistent(dto.getUsedPersistent());
		po.setUsedVolatile(dto.getUsedVolatile());

		return po;
	}
}
