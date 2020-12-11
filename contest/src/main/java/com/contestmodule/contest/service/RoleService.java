package com.contestmodule.contest.service;

import com.contestmodule.contest.entity.Role;
import com.contestmodule.contest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        Role role = roleRepository.findRoleByName(name);
        return role;
    }
}
