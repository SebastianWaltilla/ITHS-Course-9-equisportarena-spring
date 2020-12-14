package com.contestmodule.contest.controller;

import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("admin/user")
@Validated
@RolesAllowed("ADMIN")
public class AdminUserController {

    Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    private UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find-all")
    public Iterable<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/register-admin")
    public User createAdminUser(@RequestBody @Valid User user) {
        logger.info("CreateAdminUser() was called with the email" + user.getEmail());
        return userService.save(user, true);
    }

    @DeleteMapping("delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
