package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.EmptyListException;
import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.service.ContestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/contest")
@RolesAllowed({"USER", "ADMIN"})
public class ContestController {

    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }


    @ApiOperation(value = "Contestinformation by id. Only active contests shown. User access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Contestinformation"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<ContestInfoForUserDto> findContestByID(@PathVariable Long id) {
        return contestService.findContestInfoForUserByID(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @ApiOperation(value = "List of all active contests with information concerning user. User access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 200, message = "List of active contests."),
    })
    @GetMapping("/active")
    public ResponseEntity<List<ContestInfoForUserDto>> findAllActiveContests() {
        List<ContestInfoForUserDto> list = contestService.findAllContestsForUser();

        if (list.size() == 0) {
            throw new EmptyListException("Currently no contests open.");
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}