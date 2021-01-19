package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.EmptyListException;
import com.contestmodule.contest.Exceptions.UserAlreadyExistsException;
import com.contestmodule.contest.Exceptions.UserNotFoundException;
import com.contestmodule.contest.dto.UserInfoForAdminDto;
import com.contestmodule.contest.dto.UserNoPasswordDto;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<UserInfoForAdminDto>> findAllUsers() {
        List<UserInfoForAdminDto> users = userService.findAllUsersAsUserInfoForAdminDto();

        if (users.size() == 0)
            throw new EmptyListException("No users found.");

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register-admin")
    public UserNoPasswordDto createAdminUser(@RequestBody @Valid User user) {
        logger.info("CreateAdminUser() was called with the email" + user.getEmail());
        if (userService.getUserByUsername(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("This email is already registered!");
        } else
            return new UserNoPasswordDto().getUserNoPasswordDtoFromUser(userService.save(user, true));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if (userService.findUserById(id).isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity(id + " was deleted", HttpStatus.OK);
        } else
            throw new UserNotFoundException("User not found.");
    }


}
