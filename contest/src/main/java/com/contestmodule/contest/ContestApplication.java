package com.contestmodule.contest;

import com.contestmodule.contest.dto.UserDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.entity.Role;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.repository.RoleRepository;
import com.contestmodule.contest.service.ContestService;
import com.contestmodule.contest.service.EntryService;
import com.contestmodule.contest.service.UserService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class ContestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContestApplication.class, args);
    }


    @Bean
    public CommandLineRunner demoData(ContestService service, EntryService entryService) {

        return args -> {

            var today = LocalDate.now();

            Contest contest1 = new Contest("First contest, active",
                    "demotävling",
                    10,
                    today,
                    today.plusDays(7),
                    new BigDecimal("10.00"), "en kram",
                    "enkel", "adminkommentar");

            Contest contest2 = new Contest("secondcontest, future",
                    "demotävling2",
                    10,
                    today.plusMonths(1),
                    today.plusMonths(1).plusDays(5),
                    new BigDecimal("10.00"), "en kram",
                    "enkel", "adminkommentar");

            Contest contest3 = new Contest("thirdcontest, future",
                    "demotävling3",
                    10,
                    today.plusMonths(1),
                    today.plusMonths(1).plusDays(5),
                    new BigDecimal("10.00"), "en kram",
                    "enkel", "adminkommentar");

            service.createContest(contest1);
            service.createContest(contest2);
            service.createContest(contest3);



            Entry entry1 = new Entry(1L,
                    contest1,
                    "usercomment",
                    false, "Rolf");

            Entry entry2 = new Entry(2L,
                    contest1,
                    "hej hej jag kommer vinna",
                    false, "Bert");

            Entry entry3 = new Entry(2L,
                    contest2,
                    "jag är bäst",
                    false, "Bert");




            entryService.createEntry(entry1);
            entryService.createEntry(entry2);
            entryService.createEntry(entry3);

        };
    }
//    @Bean //TODO Tabort / Skapa default adminkonton
//    public CommandLineRunner demoData2(UserService service, RoleRepository roleRepository) {
//        return args -> {
//            Role role = new Role("ADMIN");
//            Role role2 = new Role("USER");
//
//            roleRepository.save(role);
//            roleRepository.save(role2);
//
//            UserDto user1 = new UserDto("Sune", "Rolfsson", "administrationsvägen 1", "sune@admin.se", "password");
//
//            user1.getRoles().add(roleRepository.findRoleByName("ADMIN"));
//
//            service.save(user1);
//
//            User user2 = new User("Anders", "Andersson", "hejvägen 2", "user", "user");
//
//           // user2.getRoles().add(roleRepository.findByName("USER"));
//
//            service.createUser(user2);
//
//        };
//    }

    @Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

