package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.exceptions.ResourceNotFoundException;
import com.laurenemick.pintreach.models.Role;
import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.UserMinimum;
import com.laurenemick.pintreach.models.UserRoles;
import com.laurenemick.pintreach.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    UserRepository userrepos;

    @Autowired
    RoleService roleservice;

    @Override
    public List<User> listAll() {
        List<User> myList= new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(myList::add);
        return myList;
    }

    @Override
    public User findByUsername(String name) {
        return userrepos.findByUsername(name);
    }

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public User findById(long id) {
        return userrepos.findById(id).orElseThrow(()->new ResourceNotFoundException("User " + id + " not found"));
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getUserid() != 0)
        {
            userrepos.findById(user.getUserid())
                .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername()
            .toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail()
            .toLowerCase());
        newUser.setImageurl(user.getImageurl());

        newUser.getRoles()
            .clear();
        for (UserRoles ur : user.getRoles())
        {
            Role addRole = roleservice.findByName("USER");
            newUser.getRoles()
                .add(new UserRoles(newUser, addRole));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(
        UserMinimum user,
        long id)
    {
        User updateUser = findById(id);

        if (user.getPassword() != null && user.getPassword() != "")
        {
            updateUser.setPassword(user.getPassword());
        }

        if (user.getPrimaryemail() != null)
        {
            updateUser.setPrimaryemail(user.getPrimaryemail()
                .toLowerCase());
        }

        if (user.getImageurl() != null)
        {
            updateUser.setImageurl(user.getImageurl());
        }

        return userrepos.save(updateUser);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userrepos.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        userrepos.deleteAll();
    }
}