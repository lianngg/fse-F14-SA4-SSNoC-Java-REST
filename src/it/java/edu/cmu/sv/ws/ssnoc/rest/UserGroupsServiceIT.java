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
public class UserGroupsServiceIT {
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
            content = "{\"userName\":\"userA\",\"password\":\"123456\",\"createdAt\":\"10/01/2014 16:15:30\"}")
    public void test01Signup() {
        assertCreated(response);
    }
    
    @HttpTest(method = Method.POST, path = "/user/signup", type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"userB\",\"password\":\"123456\",\"createdAt\":\"10/01/2014 16:15:30\"}")
    public void test02Signup() {
        assertCreated(response);
    }
    
    @HttpTest(method = Method.POST, path = "/user/signup", type = MediaType.APPLICATION_JSON, 
            content = "{\"userName\":\"userC\",\"password\":\"123456\",\"createdAt\":\"10/01/2014 16:15:30\"}")
    public void test03Signup() {
        assertCreated(response);
    }

    
    @HttpTest(method = Method.POST, path = "/message/A/B", type = MediaType.APPLICATION_JSON, 
            content = "{\"content\":\"Hello from A to B\",\"postedAt\":\"10/01/2014 16:15:30\"}")
    public void test04AtoB() {
        System.out.println(response.getBody());
    }
    
    @HttpTest(method = Method.POST, path = "/message/B/A", type = MediaType.APPLICATION_JSON, 
            content = "{\"content\":\"Hello from B to A\",\"postedAt\":\"10/01/2014 16:15:30\"}")
    public void test05BtoA() {
        System.out.println(response.getBody());
    }
    
    @HttpTest(method = Method.GET, path = "/usergroups/unconnected")
    public void test06SocaiNetwork() {
        System.out.println(response.getBody());
    }
    
}
