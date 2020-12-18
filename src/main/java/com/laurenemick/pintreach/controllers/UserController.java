package com.laurenemick.pintreach.controllers;

import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.UserMinimum;
import com.laurenemick.pintreach.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController
{
    @Autowired
    UserService userService;

    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myList = userService.listAll();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}",
        produces = {"application/json"})
    public ResponseEntity<?> getUserById(
        @PathVariable
            Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
            HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value = "/myinfo")
    public ResponseEntity<?> getUserInfo(Authentication authentication)
    {
        User u = userService.findByUsername(authentication.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

//    @PostMapping(value = "/user", consumes = "application/json")
//    public ResponseEntity<?> addNewUser(@Valid @RequestBody User newUser) {
//        newUser.setUserid(0);
//        newUser = userService.save(newUser);
//
//        // set the location header for the newly created resource
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newUserURI = ServletUriComponentsBuilder
//            .fromCurrentRequest()
//            .path("/{userid}")
//            .buildAndExpand(newUser.getUserid())
//            .toUri();
//        responseHeaders.setLocation(newUserURI);
//
//        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
//    }

    @PutMapping(value = "/user/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@Valid @RequestBody
                                            UserMinimum user, @PathVariable long id)
    {
        User newUser = userService.update(user, id);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
