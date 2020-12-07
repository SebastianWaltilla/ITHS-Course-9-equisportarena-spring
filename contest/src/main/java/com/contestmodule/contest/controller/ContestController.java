package com.contestmodule.contest.controller;

import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contest")
public class ContestController {

    Logger logger = LoggerFactory.getLogger(ContestService.class);

    private ContestService contestService;

    public ContestController(ContestService contestService){
        this.contestService = contestService;
    }

@GetMapping("/id/{id}")
    public Optional<Contest> findContestByID(@PathVariable Long id) {
        return contestService.findContestByID(id); // Vid ett senare tillfälle lägg till en extra check så man inte kan få ut tävlingar som inte är aktiva.
    }

@GetMapping("/active")
    public Iterable<Contest> findAllActiveContests(){
        return contestService.findAllContestsForUser();
}

}
