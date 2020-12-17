package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.EntryNotFoundException;
import com.contestmodule.contest.Exceptions.UserAlreadyInContestException;
import com.contestmodule.contest.dto.EntryDto;
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
import java.util.Optional;

@RestController
@RequestMapping("/entry")
@RolesAllowed("USER")
public class EntryController {

    Logger logger = LoggerFactory.getLogger(EntryService.class);

    private EntryService entryService;
    private ContestService contestService;
    private UserService userService;
    private ObjectMapper objectMapper;

    public EntryController(EntryService entryService, ContestService contestService, UserService userService, ObjectMapper objectMapper) {
        this.entryService = entryService;
        this.contestService = contestService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

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
                    "User has already created an entry (with id" + entryDto.getUserId() + ").");
        }
    }

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

}
