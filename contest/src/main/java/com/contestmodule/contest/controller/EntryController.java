package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.EntryNotFoundException;
import com.contestmodule.contest.Exceptions.UserAlreadyInContestException;
import com.contestmodule.contest.dto.EntryDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.service.ContestService;
import com.contestmodule.contest.service.EntryService;
import com.contestmodule.contest.service.UserService;
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

    public EntryController(EntryService entryService, ContestService contestService, UserService userService) {
        this.entryService = entryService;
        this.contestService = contestService;
        this.userService = userService;

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

    @PatchMapping("/update-entry")
    public ResponseEntity<Entry> updateEntry(@RequestBody EntryDto updatedEntry) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long id = userService.getUserByUsername(auth.getName()).getId();

        Optional<Entry> entryOptional = entryService.findEntryById(updatedEntry.getContestId());
        if (!entryOptional.isPresent()) {
            throw new EntryNotFoundException("Entry with id: " + id + " not found");
        } else {
            Entry entry = entryOptional.get();
            if (updatedEntry.getVideoLink() != null) {
                entry.setVideolink(updatedEntry.getVideoLink());
            }
            if (updatedEntry.getHorsename() != null) {
                entry.setHorseName(updatedEntry.getHorsename());
            }
            if (updatedEntry.getUserComment() != null) {
                entry.setUserComment(updatedEntry.getUserComment());
            }
            entryService.createEntry(entry);
            return ResponseEntity.ok().build();
        }
    }
}
