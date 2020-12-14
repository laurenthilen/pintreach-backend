package com.laurenemick.pintreach.repositories;

import com.laurenemick.pintreach.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>
{
    Role findByNameIgnoreCase(String name);
}