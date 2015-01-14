package edu.cmu.sv.ws.ssnoc.rest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.common.utils.ConverterUtils;
import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMessageDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MessagePO;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.MessagesService;

public class MessagesServiceTest {
    private MessagesService messagesService;
    private IMessageDAO dao;
    private User userC1;
    private User userC2;
    private User userC3;
    UserService userService;
    
    @Before
    public void setUp() throws Exception {
        SystemStateUtils.inMemoryDatabaseMode = true;
        DBUtils.initializeDatabase();
        messagesService = new MessagesService();
        dao = DAOFactory.getInstance().getMessageDAO();
        userService = new UserService();
        userC1 = new User();
        userC1.setUserName("userC1");
        userC1.setPassword("123456");
        userC1.setCreatedAt(new Date().getTime() / 1000);
        userC1.setAccountStatus("ACTIVE");
        userService.addUser(userC1);
        userC2 = new User();
        userC2.setUserName("userC2");
        userC2.setPassword("123456");
        userC2.setCreatedAt(new Date().getTime() / 1000);
        userC2.setAccountStatus("ACTIVE");
        userService.addUser(userC2);
    }

    @After
    public void tearDown() throws Exception {
        Connection conn = DBUtils.getConnection();
        conn.createStatement().execute("SHUTDOWN");
        DBUtils.DB_TABLES_EXIST = false;
    }

    @Test
    public void noMessagesOnWallAtTheBeginning() {
        assertTrue(messagesService.loadMessagesOnWall().isEmpty());
    }
    
    @Test
    public void noMessagesOnWallVisibleAtTheBeginning() {
        assertTrue(messagesService.loadMessagesOnWallVisible().isEmpty());
    }
    
    @Test
    public void noAnnouncementAtTheBeginning() {
        assertTrue(messagesService.loadMessagesAsAnnouncement().isEmpty());
    }
    
    @Test
    public void noAnnouncementVisibleAtTheBeginning() {
        assertTrue(messagesService.loadMessagesAsAnnouncementVisible().isEmpty());
    }
    
    @Test
    public void noPrivateChatMessagesExchangedBetweenTwoCitizensAtTheBeginning(){
        assertTrue(messagesService.loadMessagesBetweenTwoUsers("userC1", "userC2").isEmpty());
    }
    
    @Test
    public void noPrivateChatMessagesExchangedBetweenTwoCitizensVisibleAtTheBeginning(){
        assertTrue(messagesService.loadMessagesBetweenTwoUsersVisible("userC1", "userC2").isEmpty());
    }    

    @Test
    public void canRetrieveMessageOnPublicWall() {    
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO,SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        assertTrue(messagesService.loadMessagesOnWall().contains(testMessage));
    }
    
    public void canRetrieveMessageOnPublicWallEvenIfTheUserIsInactive() {    
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO,SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        User user = new User();
        user.setAccountStatus("INACTIVE");
        userService.updateUser("userC1", user);
        assertTrue(messagesService.loadMessagesOnWall().contains(testMessage));
    }
    
    @Test
    public void canRetrieveMessageOnPublicWallVisible() {    
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO,SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        assertTrue(messagesService.loadMessagesOnWallVisible().contains(testMessage));
    }
    
    @Test
    public void canNotRetrieveMessageOnPublicWallVisibleIfTheUserIsInactive() { 
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC2");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO,SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        User user = new User();
        user.setAccountStatus("INACTIVE");
        userService.updateUser("userC2", user);
        assertFalse(messagesService.loadMessagesOnWallVisible().contains(testMessage));
    }
    
    @Test
    public void canRetrieveAllMessagesOnPublicWall() {     
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        MessagePO testMessagePO2 = new MessagePO();
        testMessagePO2.setAuthor("userC2");
        testMessagePO2.setContent("This is a test2");
        testMessagePO2.setMessageType("WALL");
        testMessagePO2.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        dao.save(testMessagePO2, SystemStateUtils.NORMAL);
        assertEquals(messagesService.loadMessagesOnWall().size(), 2);
    }

    @Test
    public void canRetrieveAllMessagesOnPublicWallVisible() {     
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        MessagePO testMessagePO2 = new MessagePO();
        testMessagePO2.setAuthor("userC2");
        testMessagePO2.setContent("This is a test2");
        testMessagePO2.setMessageType("WALL");
        testMessagePO2.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        dao.save(testMessagePO2, SystemStateUtils.NORMAL);
        assertEquals(messagesService.loadMessagesOnWallVisible().size(), 2);
    }
    
    @Test
    public void canNotRetrieveAllMessagesOnPublicWallVisibleIfUsersAreInactive() {     
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("WALL");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        MessagePO testMessagePO2 = new MessagePO();
        testMessagePO2.setAuthor("userC2");
        testMessagePO2.setContent("This is a test2");
        testMessagePO2.setMessageType("WALL");
        testMessagePO2.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        dao.save(testMessagePO2, SystemStateUtils.NORMAL);
        User inactiveUser = new User();
        inactiveUser.setAccountStatus("INACTIVE");
        userService.updateUser("UserC1", inactiveUser);
        userService.updateUser("UserC2", inactiveUser);
        assertEquals(messagesService.loadMessagesOnWallVisible().size(), 0);
    }

    @Test
    public void canRetrieveAnAnnouncement() {     
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("ANNOUNCEMENT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        assertEquals(messagesService.loadMessagesAsAnnouncement().size(), 1);
    }
    
    @Test
    public void canRetrieveAllAnnouncements() {     
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("ANNOUNCEMENT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        MessagePO testMessagePO2 = new MessagePO();
        testMessagePO2.setAuthor("userC2");
        testMessagePO2.setContent("This is a test2");
        testMessagePO2.setMessageType("ANNOUNCEMENT");
        testMessagePO2.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        dao.save(testMessagePO2, SystemStateUtils.NORMAL);
        assertEquals(messagesService.loadMessagesAsAnnouncement().size(), 2);
    }
    
    @Test
    public void canNotRetrieveAnnouncementVisibleIfTheUserIsInactive() { 
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC2");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("ANNOUNCEMENT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO,SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        User user = new User();
        user.setAccountStatus("INACTIVE");
        userService.updateUser("userC2", user);
        assertFalse(messagesService.loadMessagesAsAnnouncementVisible().contains(testMessage));
    }
    
    @Test
    public void canNotRetrieveAllAnnouncementsVisibleIfUsersAreInactive() { 
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC2");
        testMessagePO.setContent("This is a test");
        testMessagePO.setMessageType("ANNOUNCEMENT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO,SystemStateUtils.NORMAL);
        MessagePO testMessagePO2 = new MessagePO();
        testMessagePO2.setAuthor("userC1");
        testMessagePO2.setContent("HIHIHI");
        testMessagePO2.setMessageType("ANNOUNCEMENT");
        testMessagePO2.setPostedAt(new Date().getTime() / 1000);
        dao.save(testMessagePO,SystemStateUtils.NORMAL);
        User user = new User();
        user.setAccountStatus("INACTIVE");
        userService.updateUser("userC1", user);
        userService.updateUser("userC2", user);
        assertEquals(messagesService.loadMessagesAsAnnouncementVisible().size(), 0);
    }
    
    @Test
    public void canReceiveANewPrivateMessageFromOthers(){
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setTarget("userC2");
        testMessagePO.setContent("Hello from userC1 to userC2");
        testMessagePO.setMessageType("CHAT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        testMessagePO.setMessageId(1);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        assertTrue(messagesService.loadMessagesBetweenTwoUsers("userC2", "userC1").contains(testMessage));
    }
    
    @Test
    public void canReceiveANewPrivateMessageVisibleFromOthers(){
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setTarget("userC2");
        testMessagePO.setContent("Hello from userC1 to userC2");
        testMessagePO.setMessageType("CHAT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        testMessagePO.setMessageId(1);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        Message testMessage = ConverterUtils.convert(testMessagePO);
        assertTrue(messagesService.loadMessagesBetweenTwoUsersVisible("userC2", "userC1").contains(testMessage));
    }

    @Test
    public void canNotReceiveANewPrivateMessageVisibleFromOthers(){
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setTarget("userC2");
        testMessagePO.setContent("Hello from userC1 to userC2");
        testMessagePO.setMessageType("CHAT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        testMessagePO.setMessageId(1);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        User user = new User();
        user.setAccountStatus("INACTIVE");
        userService.updateUser("userC1", user);
        Message testMessage = ConverterUtils.convert(testMessagePO);    
        assertFalse(messagesService.loadMessagesBetweenTwoUsersVisible("userC2", "userC1").contains(testMessage));
    }
    
    @Test
    public void canReceiveAllPrivateMessagesFromOthers(){
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setTarget("userC2");
        testMessagePO.setContent("Hello from userC1 to userC2");
        testMessagePO.setMessageType("CHAT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        testMessagePO.setMessageId(1);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        MessagePO testMessagePO2 = new MessagePO();
        testMessagePO2.setAuthor("userC1");
        testMessagePO2.setTarget("userC2");
        testMessagePO2.setContent("How are you doing, C2?");
        testMessagePO2.setMessageType("CHAT");
        testMessagePO2.setPostedAt(new Date().getTime() / 1000);
        testMessagePO2.setMessageId(2);
        dao.save(testMessagePO2, SystemStateUtils.NORMAL);
        assertEquals(messagesService.loadMessagesBetweenTwoUsers("userC2", "userC1").size(), 2);
    }
    
    @Test
    public void canReceiveAllPrivateMessagesVisibleFromOthers(){
        MessagePO testMessagePO = new MessagePO();
        testMessagePO.setAuthor("userC1");
        testMessagePO.setTarget("userC2");
        testMessagePO.setContent("Hello from userC1 to userC2");
        testMessagePO.setMessageType("CHAT");
        testMessagePO.setPostedAt(new Date().getTime() / 1000);
        testMessagePO.setMessageId(1);
        dao.save(testMessagePO, SystemStateUtils.NORMAL);
        MessagePO testMessagePO2 = new MessagePO();
        testMessagePO2.setAuthor("userC1");
        testMessagePO2.setTarget("userC2");
        testMessagePO2.setContent("How are you doing, C2?");
        testMessagePO2.setMessageType("CHAT");
        testMessagePO2.setPostedAt(new Date().getTime() / 1000);
        testMessagePO2.setMessageId(2);
        dao.save(testMessagePO2, SystemStateUtils.NORMAL);
        User user = new User();
        user.setAccountStatus("INACTIVE");
        userService.updateUser("userC1", user);
        assertEquals(messagesService.loadMessagesBetweenTwoUsersVisible("userC2", "userC1").size(), 0);
    }

}
