package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.User;

import java.util.List;

public interface UserService
{
    List<User> listAll();

    User findByUsername(String name);

    User findById(long id);

    User save(User user);

    User update(User user, long id);

    void delete(long id);
}
