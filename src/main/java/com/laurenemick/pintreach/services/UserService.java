package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.User;
import com.laurenemick.pintreach.models.UserMinimum;

import java.util.List;

public interface UserService
{
    List<User> listAll();

    User findByUsername(String name);

    User findUserById(long id);

    User findById(long id);

    User save(User user);

    User update(UserMinimum user, long id);

    void delete(long id);

    void deleteAll();
}
