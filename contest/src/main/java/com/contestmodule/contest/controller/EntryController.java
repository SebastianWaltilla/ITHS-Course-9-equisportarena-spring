package com.contestmodule.contest.controller;

import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.service.EntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequestMapping("/entry")
public class EntryController {

    Logger logger = LoggerFactory.getLogger(EntryService.class);

    private EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping("/create")
    public Entry createEntry(@RequestBody Entry entry) {
        logger.info("Entry created with id" + entry.getId());
        return entryService.createEntry(entry);
    }

    @GetMapping("/find-all")
    public Iterable<Entry> findAllEntries() {
        return entryService.findAllEntries();
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
