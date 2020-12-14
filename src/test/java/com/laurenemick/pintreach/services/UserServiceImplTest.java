package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.PintreachApplication;
import com.laurenemick.pintreach.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

// Service Implementation Using the Database
@RunWith(SpringRunner.class) // tell JUnit that we're doing a spring app
@SpringBootTest(classes = PintreachApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        List<User> myList = userService.listAll();
        for (User u : myList)
        {
            System.out.println(u.getUserid() + "" + u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_listAll()
    {
        assertEquals(2, userService.listAll().size());
    }

    @Test
    public void b_findByUsername()
    {
        userService.findByUsername("Lauren Emick");
        assertEquals("Lauren Emick", userService.findByUsername("Lauren Emick").getUsername());
    }

    @Test
    public void c_findById()
    {
    }

    @Test
    public void d_save()
    {
    }

    @Test
    public void e_update()
    {
    }

    @Test
    public void f_delete()
    {
    }
}