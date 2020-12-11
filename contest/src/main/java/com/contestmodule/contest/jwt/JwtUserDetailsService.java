package com.contestmodule.contest.jwt;

import java.util.ArrayList;


import com.contestmodule.contest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username);
        var roles = new ArrayList<String>();
        if (username.equals(user.getEmail())) {
            return User u = new User(user.getEmail(), user.getPassword(),


        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
