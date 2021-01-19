package com.contestmodule.contest.service;


import com.contestmodule.contest.dto.UserInfoForAdminDto;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private RoleService roleService;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserInfoForAdminDto> findAllUsersAsUserInfoForAdminDto() {
        return userRepository.findAllUsersAsInfoForAdminDto();
    }

    public void deleteUser(String userName) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(userName));
        userRepository.deleteById(foundUser.get().getId());
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            userRepository.delete(user.get());
    }


    public User getUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user, boolean isAdmin) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (isAdmin)
            user.getRoles().add(roleService.findRoleByName("ADMIN"));
        user.getRoles().add(roleService.findRoleByName("USER"));
        return userRepository.save(user);
    }

    public User saveWithoutEncryptingPassword(User user, boolean isAdmin) {
        if (isAdmin)
            user.getRoles().add(roleService.findRoleByName("ADMIN"));
        user.getRoles().add(roleService.findRoleByName("USER"));
        return userRepository.save(user);
    }


}
