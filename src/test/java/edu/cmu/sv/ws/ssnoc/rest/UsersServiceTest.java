package edu.cmu.sv.ws.ssnoc.rest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.common.utils.SystemStateUtils;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;

public class UsersServiceTest {
    private MessageService messageService;
    private UsersService usersService;
    private User userC1;
    private User userC2;

    @Before
    public void setUp() throws Exception {
        SystemStateUtils.inMemoryDatabaseMode = true;
        DBUtils.initializeDatabase();
        usersService = new UsersService();
        messageService = new MessageService();
        UserService userService = new UserService();
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
    public void noChatBuddiesAtTheBeginning() {
        assertEquals(usersService.loadChatbuddies("userC1").size(), 0);
    }
    
    @Test
    public void othersBecomeChatBuddiesIfUserSendChatMessagesToThem(){
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("Hello from userC1 to userC2");
        testMessage.setMessageType("CHAT");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        testMessage.setTarget("userC2");
        testMessage.setMessageId(1);
        messageService.addMessageAsChat("userC1", "userC2", testMessage);
        assertTrue(usersService.loadChatbuddies("userC1").contains(userC2));
    }
    
    @Test
    public void becomeChatBuddiesIfUserReceivedPrivateMessagesButHasNeverReplied() {
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("Hello from userC1 to userC2");
        testMessage.setMessageType("CHAT");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        testMessage.setTarget("userC2");
        testMessage.setMessageId(1);
        messageService.addMessageAsChat("userC1", "userC2", testMessage);
        assertEquals(usersService.loadChatbuddies("userC2").size(), 1);
    }
    
    @Test
    public void becomeChatBuddiesOfEachOtherIfUserReceivedPrivateMessagesAndReplied() {
        Message testMessage = new Message();
        testMessage.setAuthor("userC1");
        testMessage.setContent("Hello from userC1 to userC2");
        testMessage.setMessageType("CHAT");
        testMessage.setPostedAt(new Date().getTime() / 1000);
        testMessage.setTarget("userC2");
        testMessage.setMessageId(1);
        messageService.addMessageAsChat("userC1", "userC2", testMessage);
        Message testMessage2 = new Message();
        testMessage2.setAuthor("userC2");
        testMessage2.setContent("Hello from userC2 to userC1");
        testMessage2.setMessageType("CHAT");
        testMessage2.setPostedAt(new Date().getTime() / 1000);
        testMessage2.setTarget("userC1");
        testMessage2.setMessageId(2);
        messageService.addMessageAsChat("userC2", "userC1", testMessage2);
        assertTrue(usersService.loadChatbuddies("userC2").contains(userC1));
        assertTrue(usersService.loadChatbuddies("userC1").contains(userC2));    
    }

}
