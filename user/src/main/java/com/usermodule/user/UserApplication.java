package com.usermodule.user;

import com.usermodule.user.entity.Role;
import com.usermodule.user.entity.User;
import com.usermodule.user.repository.UserRepository;
import com.usermodule.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(UserService service) {
        return args -> {
            Role role = new Role("ADMIN");
            User user1 = new User("Anders", "Andersson", "hejvägen 2", "admin", "admin");

            user1.getRoles().add(role);

            service.createUser(user1);

            Role role2 = new Role("USER");
            User user2 = new User("Anders", "Andersson", "hejvägen 2", "user", "user");

            user2.getRoles().add(role2);

            service.createUser(user2);

        };
    }

}
