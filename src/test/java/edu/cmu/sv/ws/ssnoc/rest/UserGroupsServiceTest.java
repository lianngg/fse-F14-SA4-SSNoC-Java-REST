package edu.cmu.sv.ws.ssnoc.rest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

public class UserGroupsServiceTest {
    private UserGroupsService userGroupsService;
    private MessageService messageService;
    private List<String> nodes;
    private Map<String, List<String>> edges;
    private UserService userService;
    private User userC1;
    private User userC2;
    private User userC3;
    
    @Before
    public void setUp() throws Exception {
        userGroupsService = new UserGroupsService();
        edges = new TreeMap<String, List<String>>();
        
        SystemStateUtils.inMemoryDatabaseMode = true;
        DBUtils.initializeDatabase();
        messageService = new MessageService();
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
        userC3 = new User();
        userC3.setUserName("userC3");
        userC3.setPassword("123456");
        userC3.setCreatedAt(new Date().getTime() / 1000);
        userService.addUser(userC3);
    }

    @After
    public void tearDown() throws Exception {
        Connection conn = DBUtils.getConnection();
        conn.createStatement().execute("SHUTDOWN");
        DBUtils.DB_TABLES_EXIST = false;
    }

    @Test
    public void everybodyIsInTheSameClusterAtTheBegining() {
        nodes = Arrays.asList("A", "B", "C");
        edges.put("A", Arrays.asList(""));
        edges.put("B", Arrays.asList(""));
        edges.put("C", Arrays.asList(""));
        // Only one cluster containing A, B and C
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).size(), 1);
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).iterator().next().size(), 3);
    }
    
    @Test
    public void becomeInTheDifferentClusterAfterUserAandUserBTalkToEachOther() {
        nodes = Arrays.asList("A", "B", "C");
        edges.put("A", Arrays.asList("B"));
        edges.put("B", Arrays.asList("A"));
        edges.put("C", Arrays.asList(""));
        // Two clusters
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).size(), 2);
        // A and C are in the same cluster
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).iterator().next().size(), 2);
        // B and C are in the same cluster
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).iterator().next().size(), 2);
    }
    
    @Test
    public void becomeTheOnlyOneInAClusterAfterChattingWithAllUsers() {
        nodes = Arrays.asList("A", "B", "C");
        // A sends messages to B and C
        edges.put("A", Arrays.asList("B", "C"));
        // B sends back a message to A
        edges.put("B", Arrays.asList("A"));
        // C sends back a message to A
        edges.put("C", Arrays.asList("A"));

        // Two clusters
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).size(), 2);
        // B and C are in the same cluster together
        // A is in the the cluster alone
        for( Set<String> userGroup: userGroupsService.loadUserGroupsMethod(nodes, edges)) {
            if(userGroup.size() == 1){
                assertTrue(userGroup.contains("A"));
            }
            else{
                assertTrue(userGroup.contains("B") && userGroup.contains("C"));
            }
        }
    }
    
    @Test
    public void everybodyInAClusterAloneAfterTalkingToEachOther() {
        nodes = Arrays.asList("A", "B", "C");
        // A sends messages to B and C
        edges.put("A", Arrays.asList("B", "C"));
        // B sends messages to A and C
        edges.put("B", Arrays.asList("A", "C"));
        // C sends messages to A and B
        edges.put("C", Arrays.asList("A", "B"));
        // Three clusters, each contains only one user
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).size(), nodes.size());
        for(Set<String> userGroup: userGroupsService.loadUserGroupsMethod(nodes, edges)) {
            assertEquals(userGroup.size(), 1);
        }
    }
    
    @Test
    public void moreComplexTestCaseContainingSixNodes() {
        nodes = Arrays.asList("A", "B", "C", "D", "E", "F");
        // A sends messages to E
        edges.put("A", Arrays.asList("E"));
        // B sends messages to C and E
        edges.put("B", Arrays.asList("C", "E"));
        // C sends messages to B and D
        edges.put("C", Arrays.asList("B", "D"));
        // D sends messages to C
        edges.put("D", Arrays.asList("C"));
        // E sends messages to A, B and F
        edges.put("E", Arrays.asList("A", "B", "F"));
        // F sends messages to E
        edges.put("F", Arrays.asList("E"));
        System.out.println(nodes);
        System.out.println(edges);
        System.out.println(userGroupsService.loadUserGroupsMethod(nodes, edges));
        // Four clusters: ABDF, ACF, CE and DE
        assertEquals(userGroupsService.loadUserGroupsMethod(nodes, edges).size(), 4);
        for(Set<String> userGroup: userGroupsService.loadUserGroupsMethod(nodes, edges)) {
            if(userGroup.size() == 4){
                assertTrue(!userGroup.contains("C") && !userGroup.contains("E"));
            }
            else if(userGroup.size() == 3){
                assertTrue(userGroup.contains("A"));
                assertTrue(userGroup.contains("C"));
                assertTrue(userGroup.contains("F"));
            }
            else if(userGroup.size() == 2){
                assertTrue(userGroup.contains("E"));
                assertTrue(userGroup.contains("C") || userGroup.contains("D"));
            }
        }
    }
    
    @Test
    public void testEverybodyIsInTheSameClusterAtTheBeginingWithHTTP() {
        // Only one cluster containing A, B and C
        assertEquals(userGroupsService.loadUserGroups(10).size(), 1);
    }
    
    @Test
    public void testBecomeInTheDifferentClusterAfterUserAandUserBTalkToEachOtherWithHTTP() {
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
        // Two clusters
        assertEquals(userGroupsService.loadUserGroups(10).size(), 2);
        // A and C are in the same cluster as well as Admin
        assertEquals(userGroupsService.loadUserGroups(10).iterator().next().getUser().size(), 3);
        // B and C are in the same cluster as well as Admin
        assertEquals(userGroupsService.loadUserGroups(10).iterator().next().getUser().size(), 3);
    }

}
