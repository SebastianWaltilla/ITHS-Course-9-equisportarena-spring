package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.UserAlreadyInContestException;
import com.contestmodule.contest.dto.EntryDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.service.ContestService;
import com.contestmodule.contest.service.EntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/entry")
@RolesAllowed("USER")
public class EntryController {

    Logger logger = LoggerFactory.getLogger(EntryService.class);

    private EntryService entryService;
    private ContestService contestService;

    public EntryController(EntryService entryService, ContestService contestService) {
        this.entryService = entryService;
        this.contestService = contestService;
    }

    @PostMapping("/create")
    public ResponseEntity<EntryDto> createEntry(@RequestBody EntryDto entryDto) throws UserAlreadyInContestException {

        logger.info(entryDto.getUserId().toString());
        logger.info(entryDto.getContestId() + "contest ID");

        Optional<Contest> contest = contestService.findContestByID(entryDto.getContestId());

        if(contest.isEmpty()){
            return new ResponseEntity<EntryDto>(entryDto, HttpStatus.NOT_FOUND);
        }

        Optional<Entry> entry = entryService.findEntryByUserId(entryDto.getUserId(), entryDto.getContestId());
        if(entry.isEmpty() && contest.isPresent()){
            entryService.createEntry(entryDto.getEntryFromEntryDto(contest.get()));
            return new ResponseEntity<EntryDto>(entryDto, HttpStatus.OK);
        } else {
            throw new UserAlreadyInContestException(
                    "User has already created an entry (with id" + entryDto.getUserId() + ").");
        }
    }



    @PatchMapping("/update-entry/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable("id") Long id, @RequestBody Entry updatedEntry) {
        Optional<Entry> entryOptional = entryService.findEntryById(id);
        if (!entryOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Entry entry = entryOptional.get();
        if (updatedEntry.getVideolink() != null) {
            entry.setVideolink(updatedEntry.getVideolink());
        }
        if (updatedEntry.getHorseName() != null) {
            entry.setHorseName(updatedEntry.getHorseName());
        }
        if (updatedEntry.getUserComment() != null) {
            entry.setUserComment(updatedEntry.getUserComment());
        }
        entryService.createEntry(entry);

        return ResponseEntity.noContent().build();
    }

}
