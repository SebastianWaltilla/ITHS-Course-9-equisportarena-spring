package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.CurrentlyNoActiveContestsException;
import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.service.ContestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/contest")
@RolesAllowed({"USER", "ADMIN"})
public class ContestController {

    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ContestInfoForUserDto> findContestByID(@PathVariable Long id) {
        return contestService.findContestInfoForUserByID(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/active")
    public ResponseEntity<List<ContestInfoForUserDto>> findAllActiveContests() {
        List<ContestInfoForUserDto> list = contestService.findAllContestsForUser();

        if (list.size() == 0) {
            throw new CurrentlyNoActiveContestsException("Currently no contests open.");
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}