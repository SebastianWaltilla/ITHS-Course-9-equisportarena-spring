package com.contestmodule.contest.controller;

import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.Role;
import com.contestmodule.contest.entity.SimpleContestDao;
import com.contestmodule.contest.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contest")
@RolesAllowed({Role.USER, Role.ADMIN})
public class ContestController {

    Logger logger = LoggerFactory.getLogger(ContestService.class);

    private ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping("/id/{id}")
    public Optional<Contest> findContestByID(@PathVariable Long id) {
        return contestService.findContestByID(id);  //TODO Vid ett senare tillfälle lägg till en extra check så man inte kan få ut tävlingar som inte är aktiva.
    }

    @GetMapping("/active")
    public Iterable<SimpleContestDao> findAllActiveContests() {
        return contestService.findAllContestsForUser();

    }


    @GetMapping("/active2")
    public List<ContestInfoForUserDto> findAllActiveContests2() {
        return contestService.findAllContestsForUser2();

    }
}
