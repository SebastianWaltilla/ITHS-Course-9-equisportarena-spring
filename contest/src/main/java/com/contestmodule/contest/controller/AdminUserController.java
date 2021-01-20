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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("admin/user")
@Validated
@RolesAllowed("ADMIN")
public class AdminUserController {

    Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Information about users. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of users"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/find-all")
    public ResponseEntity<List<UserInfoForAdminDto>> findAllUsers() {
        List<UserInfoForAdminDto> users = userService.findAllUsersAsUserInfoForAdminDto();

        if (users.size() == 0)
            throw new EmptyListException("No users found.");

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @ApiOperation(value = "Register new user with admin access. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "firstname, lastname, address, email"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Email already exists. This email is already registered!"),
            @ApiResponse(code = 400, message = "Bad request. Email must not be empty/ Password must not be empty")
    })
    @PostMapping("/register-admin")
    public UserNoPasswordDto createAdminUser(@RequestBody @Valid User user) {
        logger.info("CreateAdminUser() was called with the email" + user.getEmail());
        if (userService.getUserByUsername(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("This email is already registered!");
        } else
            return new UserNoPasswordDto().getUserNoPasswordDtoFromUser(userService.saveWithPasswordEncryption(user, true));
    }


    @ApiOperation(value = "Delete user by id. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User with id {id} was deleted."),
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        if (userService.findUserById(id).isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity("User with id "+id + " was deleted", HttpStatus.OK);
        } else
            throw new UserNotFoundException("User not found.");
    }


}
