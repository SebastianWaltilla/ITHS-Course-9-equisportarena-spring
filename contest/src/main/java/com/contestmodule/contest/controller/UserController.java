package com.contestmodule.contest.controller;


import com.contestmodule.contest.Exceptions.UserAlreadyExistsException;
import com.contestmodule.contest.Exceptions.UserNotFoundException;
import com.contestmodule.contest.dto.UserDto;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RolesAllowed("USER")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PermitAll
    @PostMapping("/create")
    public User createUser(@Valid @RequestBody User user) {
        logger.info("createUser() was called with username: " + user.getEmail());
        if (userService.getUserByUsername(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("This email is already registered!");
        } else
            return userService.save(user, false);
    }

    @GetMapping("/id/{id}")
    public Optional<User> findUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent())
            return user;
        else
            throw new UserNotFoundException("This user does not exist!");
    }

    @DeleteMapping("/delete")
    public void deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        userService.deleteUser(username);
    }
}
