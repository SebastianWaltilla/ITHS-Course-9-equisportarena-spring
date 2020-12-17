package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.EntryNotFoundException;
import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.service.ContestService;
import com.contestmodule.contest.service.EntryService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/admin/entry")
@RolesAllowed("ADMIN")
public class EntryAdminController {



    Logger logger = LoggerFactory.getLogger(EntryService.class);

    private EntryService entryService;
    private ObjectMapper objectMapper;

    public EntryAdminController(EntryService entryService, ObjectMapper objectMapper) {
        this.entryService = entryService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/find-all")
    public Iterable<Entry> findAllEntries() {
        return entryService.findAllEntries();
    }

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

    @PatchMapping("/update/{entryid}")
    public ResponseEntity<Entry> updateEntry2(@Valid @PathVariable("entryid") Long id, @RequestBody Entry updatedEntry2) throws JsonProcessingException {

        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        Optional<Entry> entryOptional = entryService.findEntryById(id);

        if (!entryOptional.isPresent()) {
            throw new EntryNotFoundException("Entry with id: " + id + " not found");
        } else {
            Entry entry = entryOptional.get();
            objectMapper.readerForUpdating(entry).readValue(objectMapper.writeValueAsString(updatedEntry2));
            entryService.createEntry(entry);
            return ResponseEntity.ok().build();
        }
    }
}
