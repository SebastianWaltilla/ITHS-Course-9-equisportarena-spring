package com.contestmodule.contest.controller;


import com.contestmodule.contest.Exceptions.UserAlreadyExistsException;
import com.contestmodule.contest.Exceptions.UserNotFoundException;
import com.contestmodule.contest.dto.UserNoPasswordDto;
import com.contestmodule.contest.emailhandler.OnRegistrationCompleteEvent;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/user")
@RolesAllowed({"USER", "ADMIN"})
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;

    public UserController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @ApiOperation(value = "Register new user. Email and password required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully created!"),
            @ApiResponse(code = 400, message = "Validation failed, [details]")
    })
    @PermitAll
    @PostMapping("/register")
    public String createUser(@Valid @RequestBody User user, HttpServletRequest request) {
        logger.info("createUser() was called with username: " + user.getEmail());
        if (userService.getUserByUsername(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("This email is already registered!");
        } else {
            User userCreated = userService.saveWithPasswordEncryption(user, false);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userCreated,
                    request.getLocale(), appUrl));
            return "User successfully created!";
        }
    }

    @ApiOperation(value = "Personal information about user identified by JWT Token.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully created!"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<UserNoPasswordDto> findUserById() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());

        if (user != null) {
            UserNoPasswordDto simpleUser = new UserNoPasswordDto().getUserNoPasswordDtoFromUser(user);
            return new ResponseEntity<>(simpleUser, HttpStatus.OK);
        } else
            throw new UserNotFoundException("This user does not exist!");
    }
    @ApiOperation(value = "Delete account and personal information about user identified by JWT Token.")
    @DeleteMapping("/delete")
    public void deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        userService.deleteUser(username);
    }
}
