package com.laurenemick.pintreach;

import com.laurenemick.pintreach.models.Role;
import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.UserRoles;
import com.laurenemick.pintreach.services.RoleService;
import com.laurenemick.pintreach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        User u1 = new User("admin",
            "password",
            "admin@admin.com",
            "http");
        u1.getRoles()
            .add(new UserRoles(u1, r1));
        u1.getRoles()
            .add(new UserRoles(u1, r2));
        u1.getRoles()
            .add(new UserRoles(u1, r3));
        userService.save(u1);

        // data, user
        User u2 = new User("Lauren Emick",
            "password",
            "lauren@emick.com",
            "http");
        u2.getRoles()
            .add(new UserRoles(u2, r2));
        u2.getRoles()
            .add(new UserRoles(u2, r3));
        userService.save(u2);

        /************
         * Seed Articles
         ************/
    }
}