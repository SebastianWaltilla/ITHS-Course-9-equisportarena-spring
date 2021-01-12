package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.UserNotFoundException;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.service.ContestService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/admin/contest")
@Validated
@RolesAllowed("ADMIN")
public class AdminContestController {

    Logger logger = LoggerFactory.getLogger(AdminContestController.class);


    private final ObjectMapper objectMapper;

    private final ContestService contestService;

    public AdminContestController(ContestService contestService, ObjectMapper objectMapper) {
        this.contestService = contestService;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "List of all contests.", response = Iterable.class)
    @GetMapping("/find-all")
    public Iterable<Contest> findAllContests() {
        return contestService.findAllContests();
    }

    @ApiOperation(value = "Create new contest", response = Contest.class)
    @PostMapping("/create")                 //@Validation
    public Contest createContest(@RequestBody @Valid Contest contest) {
        logger.info("createContest() was called with contestname: " + contest.getName());
        return contestService.createContest(contest);
    }

    @ApiOperation(value = "Partial update of contest.", response = Contest.class)
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

            return new ResponseEntity<>(contest, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /*
    * References:
    * https://stackoverflow.com/questions/52951336/error-in-json-result-for-one-to-many-mapping-in-spring-data-jpa
    * https://www.baeldung.com/spring-rest-json-patch
    * */
    @ApiOperation(value = "Partial update of contest.", response = Contest.class)
    @PatchMapping(path = "/partial-update-contest-with-jsonpatch/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Contest> partialUpdateContest(@PathVariable Long id, @RequestBody JsonPatch patch) {
        try {

            Contest contest = contestService.findContestByID(id).orElseThrow(() -> new UserNotFoundException("Contest not found"));
            Contest contestPatched = applyPatchToContest(patch, contest);
            logger.info("contestPatched.getName(): " + contestPatched.getName());
            logger.info(contestPatched.toString());

            contestService.updateContest(contestPatched);
            return ResponseEntity.ok(contestPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    private Contest applyPatchToContest(JsonPatch patch, Contest targetContest) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetContest, JsonNode.class));
        return objectMapper.treeToValue(patched, Contest.class);
    }











    @ApiOperation(value = "Delete Contest by contest id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "{id} was deleted"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found") })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteContest(@PathVariable Long id) {
        if (contestService.findContestByID(id).isPresent()) {
            contestService.deleteContest(id);
            return new ResponseEntity(id + " was deleted", HttpStatus.OK);
        } return ResponseEntity.notFound().build();
    }
}

