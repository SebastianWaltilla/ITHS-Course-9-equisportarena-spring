package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.EntryNotFoundException;
import com.contestmodule.contest.Exceptions.UserAlreadyInContestException;
import com.contestmodule.contest.Exceptions.UserNotFoundException;
import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.dto.EntryDto;
import com.contestmodule.contest.dto.EntryWithContestNameDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.service.ContestService;
import com.contestmodule.contest.service.EntryService;
import com.contestmodule.contest.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/entry")
@RolesAllowed("USER")
public class EntryController {

    Logger logger = LoggerFactory.getLogger(EntryService.class);

    private final EntryService entryService;
    private final ContestService contestService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public EntryController(EntryService entryService, ContestService contestService, UserService userService, ObjectMapper objectMapper) {
        this.entryService = entryService;
        this.contestService = contestService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @ApiOperation(value = "User create entry to competition.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry object"),
            @ApiResponse(code = 404, message = "Record not found, User has already created an entry in contest with id {id}"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PostMapping("/create")
    public ResponseEntity<EntryDto> createEntry(@RequestBody EntryDto entryDtoIn) throws UserAlreadyInContestException {

        EntryDto entryDto = entryDtoIn;
        Optional<Contest> contest = contestService.findContestByID(entryDto.getContestId());
        if(contest.isEmpty()){
            return new ResponseEntity<EntryDto>(entryDto, HttpStatus.NOT_FOUND);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = userService.getUserByUsername(auth.getName()).getId();

        Optional<Entry> entry = entryService.findEntryByUserId(id, entryDto.getContestId());
        if(entry.isEmpty() && contest.isPresent()){
            entryDto.setUserId(id);
            entryService.createEntry(entryDto.getEntryFromEntryDto(contest.get()));
            return new ResponseEntity<EntryDto>(entryDto, HttpStatus.OK);
        } else {
            throw new UserAlreadyInContestException(
                    "User has already created an entry in contest with id " + entryDto.getContestId() + ".");
        }
    }

    @ApiOperation(value = "User update entry to competition.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Record not found, User has already created an entry in contest with id {id}"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @PatchMapping("/update/{contestid}")
    public ResponseEntity<Entry> updateEntry(@PathVariable("contestid") Long contestid, @RequestBody EntryDto updatedEntry) throws JsonProcessingException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = userService.getUserByUsername(auth.getName()).getId();

        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        Optional<Entry> entryOptional = entryService.findEntryByUserIdAndContestId(id, contestid);

        Entry newInformationEntry = updatedEntry.getEntryFromEntryDto(entryOptional.get().getContest());

        if (!entryOptional.isPresent()) {
            throw new EntryNotFoundException("Entry not found");
        } else {
            Entry entry = entryOptional.get();
            objectMapper.readerForUpdating(entry).readValue(objectMapper.writeValueAsString(newInformationEntry));
            entryService.createEntry(entry);
            return ResponseEntity.ok().build();
        }
    }

    @ApiOperation(value = "All entries by user identified by JWT token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of entries"),
            @ApiResponse(code = 401, message = "Unauthorized")
            })
    @GetMapping("/my-entries")
    public Iterable<EntryWithContestNameDto> getMyContests(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = userService.getUserByUsername(auth.getName()).getId();

        Iterable<EntryWithContestNameDto> contestList = entryService.findAllEntriesByUserId(userId);
        return contestList;
    }

}
