package com.contestmodule.contest;

import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.service.ContestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class ContestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContestApplication.class, args);
    }


    @Bean
    public CommandLineRunner demoData(ContestService service){

        return args -> {

            Contest contest1 = new Contest("firstcontest",
                    "demotävling",
                    10,
                    LocalDate.of(2020, 12,2),
                    LocalDate.of(2020,12,24),
                    new BigDecimal(10.00),"en kram",
                    "enkel","adminkommentar");

            Contest contest2 = new Contest("secondcontest",
                    "demotävling2",
                    10,
                    LocalDate.of(2020, 10,1),
                    LocalDate.of(2020,10,2),
                    new BigDecimal(10.00),"en kram",
                    "enkel","adminkommentar");

            Contest contest3 = new Contest("thirdcontest",
                    "demotävling3",
                    10,
                    LocalDate.of(2020, 12,25),
                    LocalDate.of(2020,12,26),
                    new BigDecimal(10.00),"en kram",
                    "enkel","adminkommentar");



            service.createContest(contest1);
            service.createContest(contest2);
            service.createContest(contest3);

        };
    }
}
