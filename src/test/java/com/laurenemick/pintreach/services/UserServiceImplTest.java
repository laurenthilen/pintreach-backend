package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.PintreachApplication;
import com.laurenemick.pintreach.models.Role;
import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.UserRoles;
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
        assertEquals("admin", userService.findById(4)
            .getUsername());
    }

    @Test
    public void d_save()
    {
        Role r2 = new Role("user");
        Role r3 = new Role("data");
        r2.setRoleid(2);
        r3.setRoleid(3);

        User u5 = new User("james",
            "password",
            "james@james.com",
            "https");
        u5.getRoles().add(new UserRoles(u5, r2));
        u5.getRoles().add(new UserRoles(u5, r3));
        User saveu5 = userService.save(u5);

        System.out.println("*** DATA ***");
        System.out.println(saveu5);
        System.out.println("*** DATA ***");

        assertEquals("james", u5.getUsername());
    }

    @Test
    public void e_update()
    {
        Role r2 = new Role("user");
        Role r3 = new Role("data");
        r2.setRoleid(2);
        r3.setRoleid(3);

        User u5 = new User("james",
            "password123",
            "james@james.com",
            "https");
        u5.getRoles().add(new UserRoles(u5, r2));
        u5.getRoles().add(new UserRoles(u5, r3));
        User saveu5 = userService.save(u5);

        User updatedu5 = userService.update(u5, 6);

        System.out.println("*** DATA ***");
        System.out.println(updatedu5);
        System.out.println("*** DATA ***");

        assertEquals("james@james.com", userService.findByUsername("james").getPrimaryemail());
    }

    @Test
    public void f_delete()
    {
        userService.delete(5);
        assertEquals(2, userService.listAll().size());
    }
}