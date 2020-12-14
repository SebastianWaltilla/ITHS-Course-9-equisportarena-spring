package com.contestmodule.contest.service;


import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class UserService{

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

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        userRepository.deleteById(foundUser.get().getId());
    }

    public User getUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        logger.info(user.getEmail() + " :USERSERVICE SAVE() user.getEmail()");
        //encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        logger.info("ROLESERVICE.FINDROLEBYNAME(\"USER\"): " + roleService.findRoleByName("USER").getName());
        //add role to user
        roleService.findRoleByName("USER").addUser(user);
        return userRepository.save(user);
    }


}
