package com.laurenemick.pintreach.services;

import com.laurenemick.pintreach.models.Role;

import java.util.List;

public interface RoleService
{
    List<Role> findAll();

    Role findRoleById(long id);

    Role save(Role role);

    Role findByName(String name);
}
