package com.laurenemick.pintreach.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laurenemick.pintreach.models.Role;
import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.UserRoles;
import com.laurenemick.pintreach.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // bc of security
@AutoConfigureMockMvc // security
@WithMockUser(username = "admin", roles = {"ADMIN", "USER", "DATA"})
public class UserControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean // don't want to call the service bc it wouldn't be a unit test - it would be implementation
    private UserService userService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        // build DB (list)
        userList = new ArrayList<>();

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        // admin, data, user
        User u1 = new User("admin",
            "password",
            "admin@admin.com",
            "http");
        u1.setUserid(17);
        u1.getRoles()
            .add(new UserRoles(u1, r1));
        u1.getRoles()
            .add(new UserRoles(u1, r2));
        u1.getRoles()
            .add(new UserRoles(u1, r3));

        // data, user
        User u2 = new User("laurenemick",
            "password",
            "lauren@emick.com",
            "http");
        u2.setUserid(18);
        u2.getRoles()
            .add(new UserRoles(u2, r2));
        u2.getRoles()
            .add(new UserRoles(u2, r3));

        // add to list
        userList.add(u1);
        userList.add(u2);

        System.out.println("\n*** Seed Data ***");
        for (User u : userList)
        {
            System.out.println(u);
        }
        System.out.println("*** Seed Data ***\n");

        // add the below to do security testing:
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        String apiUrl = "/users/users";

        Mockito.when(userService.listAll())
            .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
            .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
            .andReturn(); // this could throw an exception
        String tr = r.getResponse()
            .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        Assert.assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserInfo()
    {
    }

    @Test
    public void updateUser()
    {
    }

    @Test
    public void deleteUser()
    {
    }
}