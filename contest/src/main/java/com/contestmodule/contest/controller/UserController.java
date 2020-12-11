package com.contestmodule.contest.controller;


import com.contestmodule.contest.dto.UserDto;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        logger.info("createUser() was called with username: " + user.getEmail());
        return userService.save(user);
    }

    @GetMapping("/find-all")
    public Iterable<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/id/{id}")
    public Optional<User> findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/403")
    public String error403(){
        return "403";
    }

    @GetMapping(value = "/any-page", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String returnSingleHTTPpage(){

        String path = "user/src/main/resources/templates/anyPage.html";
        String content = "dummyText";
        try {
            content = Files.readString(Paths.get(path));
            System.out.println(content);
        } catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }
}
