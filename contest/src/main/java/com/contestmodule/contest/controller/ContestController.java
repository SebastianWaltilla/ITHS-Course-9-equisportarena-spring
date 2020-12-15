package com.contestmodule.contest.controller;

import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contest")
@RolesAllowed({"USER","ADMIN"})
public class ContestController {

    Logger logger = LoggerFactory.getLogger(ContestService.class);

    private ContestService contestService;

    public ContestController(ContestService contestService){
        this.contestService = contestService;
    }

@GetMapping("/id/{id}")
    public ResponseEntity<ContestInfoForUserDto> findContestByID(@PathVariable Long id) {
        return contestService.findContestInfoForUserByID(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/active")
    public List<ContestInfoForUserDto> findAllActiveContests(){
        return contestService.findAllContestsForUser();

    }
}
