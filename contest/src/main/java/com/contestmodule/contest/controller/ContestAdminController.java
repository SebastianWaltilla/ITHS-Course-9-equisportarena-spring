package com.contestmodule.contest.controller;

import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.repository.ContestRepository;
import com.contestmodule.contest.service.ContestService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/contest")
@Validated
@RolesAllowed("ADMIN")
public class ContestAdminController {

    Logger logger = LoggerFactory.getLogger(ContestService.class);

    @Autowired
    private ObjectMapper objectMapper;

    private ContestService contestService;

    public ContestAdminController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping("/find-all")
    public Iterable<Contest> findAllContests() {
        return contestService.findAllContests();
    }

    @PostMapping("/create")                 //@Validation
    public Contest createContest(@RequestBody  Contest contest) {
        logger.info("createContest() was called with contestname: " + contest.getName());
        return contestService.createContest(contest);
    }

    @PatchMapping("/partial-update-contest/{id}")
    public ResponseEntity<Contest> partialUpdateContest(@Valid @PathVariable("id") Long id, @RequestBody Contest updateContest) throws JsonProcessingException {
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        logger.info("Id: " + id);

        Optional<Contest> contestOptional = contestService.findContestByID(id);

        logger.info("Requestbody: " + updateContest.getId());

        if(contestOptional.isPresent()){
            var contest = contestOptional.get();
            logger.info(contest.toString());
            objectMapper.readerForUpdating(contest).readValue(objectMapper.writeValueAsString(updateContest));
            contestService.updateContest(contest);

            logger.info("createContest() was called through update-contest with contestId: " + contest.getId());
            logger.info("Contest" + contest.getName() + "was updated");

            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteContest(@PathVariable Long id) {
        if (contestService.findContestByID(id).isPresent()) {
            contestService.deleteContest(id);
            return new ResponseEntity(id + " was deleted", HttpStatus.OK);
        } return ResponseEntity.notFound().build();
    }
}

