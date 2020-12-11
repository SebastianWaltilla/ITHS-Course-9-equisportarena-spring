package com.contestmodule.contest.service;


import com.contestmodule.contest.dto.UserDto;
import com.contestmodule.contest.entity.Role;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.repository.RoleRepository;
import com.contestmodule.contest.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private RoleService roleService;

    public UserService(UserRepository userRepository) {
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

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username); // getUserByUsername

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), true, true, true,
                true, getAuthority(user));
    }

    public User save(UserDto user) {

        User newUser = user.getUserFromDto();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleService.findRoleByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // TODO: 2020-12-11 admin bör skapas i en separat metod!
//        if(newUser.getEmail().split("@")[1].equals("admin.edu")){
//            role = roleService.findRoleByName("ADMIN");
//            roleSet.add(role);
//        }

        newUser.setRoles(roleSet);
        return userRepository.save(newUser);
    }
}
