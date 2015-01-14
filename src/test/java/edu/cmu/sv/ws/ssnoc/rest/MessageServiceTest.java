package edu.cmu.sv.ws.ssnoc.rest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;

public class MessageServiceTest {
    private MessageService messageService;
    private UserService userService;
    private IMessageDAO dao;
    private User userC1;
    private User userC2;
    
    @Before
    public void setUp() throws Exception {
        SystemStateUtils.inMemoryDatabaseMode = true;
        DBUtils.initializeDatabase();
        messageService = new MessageService();
        dao = DAOFactory.getInstance().getMessageDAO();
        userService = new UserService();
        userC1 = new User();
        userC1.setUserName("userC1");
        userC1.setPassword("123456");
        userC1.setCreatedAt(new Date().getTime() / 1000);
        userService.addUser(userC1);
        userC2 = new User();
        userC2.setUserName("userC2");
        userC2.setPassword("123456");
        userC2.setCreatedAt(new Date().getTime() / 1000);
        userService.addUser(userC2);
    }

    @After
    public void tearDown() throws Exception {
        Connection conn = DBUtils.getConnection();
        conn.createStatement().execute("SHUTDOWN");
        DBUtils.DB_TABLES_EXIST = false;
    }

    @Test
    public void retrieveAPublicMessageByIDEqualsNullAtTheBegining(){
        assertEquals(null, messageService.getMessageByID(1));
    }
    
    @Test
    public void canRetrieveAPublicMessageByID(){
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setTarget("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        testMessagePO.setMessageId(1);  
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        assertEquals(testMessage, messageService.getMessageByID(1));
    }
    
    @Test
    public void canRetrieveAPrivateMessageByID(){
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setTarget("userC2");
        testMessagePO.setContent("Hello from userC1 to userC2");
        testMessagePO.setMessageType("CHAT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        assertEquals(testMessage, messageService.getMessageByID(1));
    }
    @Test
    public void canPostANewPublicMessageOnPublicWall(){
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("This is a test");
        testMessage.setMessageType("WALL");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        messageService.addMessageOnPublicWall("userC1", testMessage);
        assertEquals(dao.findMessagesOnWall().size(), 1);
    }
    
    @Test
    public void canPostMultiplePublicMessagesOnPublicWall(){
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("This is a test");
        testMessage.setMessageType("WALL");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        messageService.addMessageOnPublicWall("userC1", testMessage);
        Message testMessage2 = new Message();
        testMessage2.setAuthor("userC2");
        testMessage2.setContent("This is a test2");
        testMessage2.setMessageType("WALL");
        testMessage2.setPostedAt(new Date().getTime() / 1000);
        messageService.addMessageOnPublicWall("userC2", testMessage2);
        assertEquals(dao.findMessagesOnWall().size(), 2);
    }
    
    @Test
    public void canSendANewPrivateMessageToOthers(){
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("Hello from userC1 to userC2");
        testMessage.setMessageType("CHAT");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        testMessage.setTarget("userC2");
        testMessage.setMessageId(1);
        messageService.addMessageAsChat("userC1", "userC2", testMessage);
        MessagePO testMessagePO = ConverterUtils.convert(testMessage);
        assertTrue(dao.findMessagesBetweenTwoUsers("userC2", "userC1").contains(testMessagePO));
        assertTrue(dao.findChatbuddiesByUser("userC1", 0, new Date().getTime() / 1000).contains("userC2"));    
    }
    
    @Test
    public void canSendMultipleNewPrivateMessagesToOthers(){
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("Hello from userC1 to userC2");
        testMessage.setMessageType("CHAT");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        testMessage.setTarget("userC2");
        testMessage.setMessageId(1);
        messageService.addMessageAsChat("userC1", "userC2", testMessage);
        Message testMessage2 = new Message();
        testMessage2.setAuthor("userC1");
        testMessage2.setContent("Hello from userC1 to userC2");
        testMessage2.setMessageType("CHAT");
        testMessage2.setPostedAt(new Date().getTime() / 1000);
        testMessage2.setTarget("userC2");
        testMessage2.setMessageId(2);
        messageService.addMessageAsChat("userC1", "userC2", testMessage2);
        assertEquals(dao.findMessagesBetweenTwoUsers("userC2", "userC1").size(), 2);   
    }
    
    @Test
    public void canAddAMessageAsAnnouncement() {
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("This is a test");
        testMessage.setMessageType("ANNOUNCEMENT");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        messageService.addMessageAsAnnouncement("userC1", testMessage);
        System.out.println(dao.findMessagesAsAnnouncement());
        assertEquals(dao.findMessagesAsAnnouncement().size(), 1);
    }

    @Test
    public void canAddMultipleMessagesAsAnnouncement() {
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("This is a test");
        testMessage.setMessageType("ANNOUNCEMENT");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        messageService.addMessageAsAnnouncement("userC1", testMessage);
        Message testMessage2 = new Message();
        testMessage2.setAuthor("userC1");
        testMessage2.setContent("This is a test2");
        testMessage2.setMessageType("ANNOUNCEMENT");
        testMessage2.setPostedAt(new Date().getTime() / 1000);
        messageService.addMessageAsAnnouncement("userC1", testMessage2);
        assertEquals(dao.findMessagesAsAnnouncement().size(), 2);
    }
}
