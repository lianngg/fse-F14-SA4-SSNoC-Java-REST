package edu.cmu.sv.ws.ssnoc.rest;

import static com.eclipsesource.restfuse.Assert.*;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(HttpJUnitRunner.class)
public class ExchangeInformationIT {
    @Rule
    public Destination destination = new Destination(this,
            "http://localhost:1234/ssnoc/");

    @Context
    public Response response;

    @HttpTest(method = Method.POST, path = "/user/signup", type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"userX\",\"password\":\"123456\",\"createdAt\":\"10/01/2014 16:15:30\"}")
    public void test01SignupUserX() {
        assertCreated(response);
    }
    
    @HttpTest(method = Method.POST, path = "/user/signup", type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"userY\",\"password\":\"123456\",\"createdAt\":\"10/01/2014 16:15:30\"}")
    public void test02SignupUserY() {
        assertCreated(response);
    }

    
 
    /**
     * Test "/message/userName"
     * Post a message on public wall from a user
     * If new message created: 201 Created
     */
    // Happy case
    @HttpTest(method = Method.POST, path = "/message/userX", type = MediaType.APPLICATION_JSON, 
            content = "{\"content\":\"Hello!\",\"postedAt\":\"10/01/2014 16:15:30\"}")
    public void test04PostAMessageOnPublicWall() {
        assertCreated(response);
        Assert.assertTrue(response.getBody().contains("Hello!"));
    }
    // Sad case when the use does not exist
    @HttpTest(method = Method.POST, path = "/message/noName", type = MediaType.APPLICATION_JSON, 
            content = "{\"content\":\"Hello!\",\"postedAt\":\"10/01/2014 16:15:30\"}")
    public void test05PostAMessageOnPublicWallFromNoExistingUser() {
        assertCreated(response);
    }
    
    /**
     * Test "/messages/wall"
     * Retrieve all messages posted on public wall
     * Return an array of Message
     */
    // Sad case when there is no message on public wall
    @HttpTest(method = Method.GET, path = "/messages/wall")
    public void test03RetrieveAllMessagesPostedOnPublicWallIfThereIsNoMessageOnPublicWall() {
        Assert.assertTrue(response.getBody().contains("[]"));
    }
    // Happy case
    @HttpTest(method = Method.GET, path = "/messages/wall")
    public void test06RetrieveAllMessagesPostedOnPublicWall() {
        Assert.assertTrue(response.getBody().contains("Hello!"));
    }
    
    /**
     * Test "/message/sendignUserName/receivingUserName"
     * Send a chat message to another user
     * If new message is created: 201 Created
     */
    // Happy case
    @HttpTest(method = Method.POST, path = "/message/userX/userY", type = MediaType.APPLICATION_JSON,
            content = "{\"content\":\"Hi from A to B\",\"postedAt\":\"10/01/2014 16:15:30\"}")
    public void test07SendAChatMessageToAnotherUser() {
        assertCreated(response);
    }
    // Sad case when users do not exist
    @HttpTest(method = Method.POST, path = "/message/noName/whoAreYou", type = MediaType.APPLICATION_JSON,
            content = "{\"content\":\"Hi from A to noName\",\"postedAt\":\"10/01/2014 16:15:30\"}")
    public void test08SendAChatMessageToANonExistingUser() {
        assertCreated(response);
    }
    
    /**
     * Test "/messages/userName1/userName2"
     * Retrieve all chat messages between two users
     * Return an array of Message
     */
    // Happy case
    @HttpTest(method = Method.GET, path = "/messages/userX/userY")
    public void test09RetrieveAllChatMessagesBetweenTwoUsers() {
        Assert.assertTrue(response.getBody().contains("Hi from A to B"));
    }
    // Sad case when users do not exist
    @HttpTest(method = Method.GET, path = "/messages/noOne/noBody")
    public void test10RetrieveAllChatMessagesBetweenTwoNonExistingUsers() {
        Assert.assertTrue(response.getBody().contains("[]"));
    }
    
    /**
     * Test "/users/userName/chatbuddies"
     * Retrieve all users with whom a user has chatted with
     * Return an array of User
     */
    // Happy case
    @HttpTest(method = Method.GET, path = "/users/userX/chatbuddies")
    public void test11RetrieveAllUsersWithWhomAUserHasChattedWith() {
        Assert.assertTrue(response.getBody().contains("userY"));
    }
    // Sad case when users do not exist
    @HttpTest(method = Method.GET, path = "/users/userNoName/chatbuddies")
    public void test12RetrieveAllUsersWithWhomANoExistingUserHasChattedWith() {
        Assert.assertTrue(response.getBody().contains("[]"));
    }
    
    /**
     * Test "/message/messageID"
     * Retrieve a message by ID
     * Return an array of Message
     */
    // Happy case
    @HttpTest(method = Method.GET, path = "/message/1")
    public void test13RetrieveAMessageByIDEqualsOneWhichIsOnPublicWall() {
        Assert.assertTrue(response.getBody().contains("WALL"));
    }
    // Sad case when ID does not exist
    @HttpTest(method = Method.GET, path = "/message/99999999")
    public void test14RetrieveAMessageByNonExistingID() {
        Assert.assertTrue(response.getBody().isEmpty());
    }
}
