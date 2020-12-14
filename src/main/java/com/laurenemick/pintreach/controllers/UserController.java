package com.laurenemick.pintreach.controllers;

import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController
{
    @Autowired
    UserService userServices;

    @GetMapping(value = "/users")
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myList = userServices.listAll();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value = "/myinfo")
    public ResponseEntity<?> getUserInfo( Authentication authentication)
    {
        User u = userServices.findByUsername(authentication.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PutMapping(value = "/user/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, @PathVariable long id)
    {
        User newUser = userServices.update(user, id);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id)
    {
        userServices.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
