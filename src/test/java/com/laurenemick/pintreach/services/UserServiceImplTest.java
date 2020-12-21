package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.PintreachApplication;
import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.UserMinimum;
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

import static junit.framework.TestCase.assertEquals;

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
        assertEquals("admin", userService.findByUsername("admin").getUsername());
    }

    @Test
    public void c_findById()
    {
        assertEquals("admin", userService.findById(6)
            .getUsername());
    }

    @Test
    public void d_save()
    {
        User newUser = new User("test test", "test", "test@email.com", "http");
        userService.save(newUser);
        assertEquals(3, userService.listAll().size());
    }

    @Test
    public void e_update()
    {
        UserMinimum original = new UserMinimum();

        original.setImageurl("updated http");
        original.setPassword("");
        original.setPrimaryemail("updated@updated.com");
        original.setUsername("");

        userService.update(original, 7);

        assertEquals("updated@updated.com",userService.findById(7).getPrimaryemail());
        assertEquals("updated http", userService.findById(7).getImageurl());
    }

    @Test
    public void f_delete()
    {
        userService.delete(7);
        assertEquals(2, userService.listAll().size());
    }
}