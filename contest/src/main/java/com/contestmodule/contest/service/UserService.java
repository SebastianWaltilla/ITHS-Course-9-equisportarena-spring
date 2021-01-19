package com.contestmodule.contest.service;


import com.contestmodule.contest.dto.UserInfoForAdminDto;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RoleService roleService;

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
        foundUser.ifPresent(user -> userRepository.deleteById(user.getId()));
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }


    public User getUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveWithPasswordEncryption(User user, boolean isAdmin) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLastLoggedIn(LocalDate.now());
        if (isAdmin) {
            user.getRoles().add(roleService.findRoleByName("ADMIN"));
        }
        user.getRoles().add(roleService.findRoleByName("USER"));
        return userRepository.save(user);
    }

    public void saveWithoutEncryptingPassword(User user, boolean isAdmin) {
        if (isAdmin)
            user.getRoles().add(roleService.findRoleByName("ADMIN"));
        user.getRoles().add(roleService.findRoleByName("USER"));
        userRepository.save(user);
    }

    @Async
    public void updateLastLoggedInByUserName(String email) {
        User user = userRepository.findByEmail(email);
        LocalDate today = LocalDate.now();
        if (user != null && !user.getLastLoggedIn().equals(today)){
            user.setLastLoggedIn(today);
            userRepository.save(user);
        }

    }
}
