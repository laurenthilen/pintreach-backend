package com.laurenemick.pintreach.repositories;

import com.laurenemick.pintreach.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
}