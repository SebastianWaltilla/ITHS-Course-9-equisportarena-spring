package com.contestmodule.contest.controller;

import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.service.ContestService;
import com.contestmodule.contest.service.EntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/entry")
public class EntryAdminController {

    public EntryAdminController(EntryService entryService) {
        this.entryService = entryService;
    }

    Logger logger = LoggerFactory.getLogger(EntryService.class);

    private EntryService entryService;


    @GetMapping("/find-all-by-contest-id/{id}")
    public Iterable<Entry> findAllByContestId(@PathVariable Long id) {
        return entryService.findAllEntriesByContestId(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEntry(@PathVariable Long id) {
        if (entryService.findEntryById(id).isPresent()) {
            entryService.deleteEntry(id);
            return new ResponseEntity(id + " was deleted", HttpStatus.OK);
        } return ResponseEntity.notFound().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable("id") Long id, @RequestBody Entry updatedEntry) {

        Optional<Entry> entryOptional = entryService.findEntryById(id);

        if (!entryOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Entry entry = entryOptional.get();

//        entry.setContest(updatedEntry.getContest()); //change entry, should it be here or in contestcontroller?

        entry.getContest().removeEntry(entry);
//        entry.getContest().addEntry(updatedEntry);
        updatedEntry.getContest().addEntry(entry);

        entry.setHasPaid(updatedEntry.hasUserPaid());


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
        logger.info("createEntry() was called through update-entry with entryId: " + entry.getId());
        logger.info("Entry" + entry.getUserId() + "was updated");

        return ResponseEntity.noContent().build();
    }
}
