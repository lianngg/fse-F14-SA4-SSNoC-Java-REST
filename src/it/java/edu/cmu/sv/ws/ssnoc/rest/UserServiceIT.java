package edu.cmu.sv.ws.ssnoc.rest;

import static com.eclipsesource.restfuse.Assert.*;
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
public class UserServiceIT {
    @Rule
    public Destination destination = new Destination(this,
            "http://localhost:1234/ssnoc/");

    @Context
    public Response response;

    /**
     * Test "/user/signup"
     * Register as a user in Join Community use case
     * If new user is created: 201 Created
     * If user exists: 200 OK
     */
    @HttpTest(method = Method.POST, path = "/user/signup", type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"Surya\",\"password\":\"Kiran\",\"createdAt\":\"10/01/2014 16:15:30\"}")
    public void test01Signup() {
        assertCreated(response);
    }
    
    @HttpTest(method = Method.POST, path = "/user/signup", type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"Surya\",\"password\":\"Kiran\"}")
    public void test02SignupExists() {
        assertOk(response);
    }

    /**
     * Test "/users"
     * Retrieve all users in Join Community use case
     */
    @HttpTest(method = Method.GET, path = "/users")
    public void test03UsersFound() {
        System.out.println("[Test3] Retrieve all users: " + response.getBody());
    }
    
    /**
     * Test "/user/userName/authenticate"
     * Verify password of a user in Join Community use case
     * if password is wrong: 401 Unauthorized
     * if user does not exist: 404 Not Found
     */
    @HttpTest(method = Method.POST, path = "/user/Surya/authenticate",
            type = MediaType.APPLICATION_JSON, 
            content = "{\"password\":\"12345\"}")
    public void test04Authenticate() {
        assertUnauthorized(response);
    }
    @HttpTest(method = Method.POST, path = "/user/abcdefg/authenticate",
            type = MediaType.APPLICATION_JSON, 
            content = "{\"password\":\"12345\"}")
    public void test05Authenticate() {
        assertNotFound(response);
    }
 
    /**
     * Test "/user/userName"
     * Retrieve a user's record in Join Community use case
     */
    @HttpTest(method = Method.GET, path = "/user/Surya")
    public void test06UserRecord() {
        System.out.println("[Test6] Retrieve Surya's record: " + response.getBody());
    }
    
    /**
     * Test "/user/userName"
     * Update a user's record in Share Status use case
     * If user name is updated: 201 Created
     * If user name is not updated: 200 OK
     */
    @HttpTest(method = Method.PUT, path = "/user/Surya",
            type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"Surya2\"}")
    public void test07UpdateUser() {
        assertCreated(response);
    }
    @HttpTest(method = Method.PUT, path = "/user/Surya2",
            type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"Surya2\"}")
    public void test08UpdateUser() {
        assertOk(response);
    }
    
    /**
     * Test "/status/userName"
     * Update a user's status and create a breadcrumb in Share Status use case
     * If new status breadcrumb is created: 201 Created
     */
    @HttpTest(method = Method.POST, path = "/status/Surya2",
            type = MediaType.APPLICATION_JSON, 
            content = "{\"modifiedAt\":\"2014-09-30 23:52:30\",\"statusCode\":\"YELLOW\"}")
    public void test09UpdateStatus() {
        assertCreated(response);
    }
    
    /**
     * Test "/status/crumbID"
     * Retrieve a status breadcrumb by unique ID in Share Status use case
     */
    @HttpTest(method = Method.GET, path = "/status/1")
    public void test10RetrieveStatus() {
        /*TO-DO: What is the crumbID???**/
        System.out.println("[Test10] Status breadcrumb of crumbID=1: " + response.getBody());
    }
    
    /**
     * Test "/statuscrumbs/userName"
     * Retrieve a user's status history
     */
    @HttpTest(method = Method.GET, path = "/statuscrumbs/Surya2")
    public void test11RetrieveStatusHistory() {
        /*TO-DO: What is the crumbID???**/
        System.out.println("[Test11] Array of StatusCrumb: " + response.getBody());
    }
}
