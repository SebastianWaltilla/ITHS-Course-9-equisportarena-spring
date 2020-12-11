package com.contestmodule.contest.repository;

import com.contestmodule.contest.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}
