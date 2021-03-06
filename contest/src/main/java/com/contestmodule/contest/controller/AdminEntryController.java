package com.contestmodule.contest.controller;

import com.contestmodule.contest.Exceptions.EntryNotFoundException;
import com.contestmodule.contest.dto.AdminEntryDto;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin/entry")
@RolesAllowed("ADMIN")
public class AdminEntryController {



    Logger logger = LoggerFactory.getLogger(AdminEntryController.class);

    private final EntryService entryService;
    private final ObjectMapper objectMapper;

    public AdminEntryController(EntryService entryService, ObjectMapper objectMapper) {
        this.entryService = entryService;
        this.objectMapper = objectMapper;
    }


    @ApiOperation(value = "Get all entries. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of entries"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/find-all")
    public Iterable<Entry> findAllEntries() {
        return entryService.findAllEntries();
    }


    @ApiOperation(value = "Get all entries by Contest id. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of entries"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/find-all-by-contest-id/{id}")
    public Iterable<AdminEntryDto> findAllByContestId(@PathVariable Long id) {
        return entryService.findAllEntriesByContestId(id);
    }


    @ApiOperation(value = "Delete entry by entryId. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Entry with id {id} was deleted"),
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEntry(@PathVariable Long id) {
        if (entryService.findEntryById(id).isPresent()) {
            entryService.deleteEntry(id);
            return new ResponseEntity("Entry with id " + id + " was deleted", HttpStatus.OK);
        } return ResponseEntity.notFound().build();
    }


    @ApiOperation(value = "Update entry-information. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "(No body)"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "500 internal server error"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PatchMapping("/update/{entryid}")
    public ResponseEntity<Entry> updateEntry2(@Valid @PathVariable("entryid") Long id, @RequestBody Entry updatedEntry2) throws JsonProcessingException {

        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY);
        Optional<Entry> entryOptional = entryService.findEntryById(id);

        if (entryOptional.isEmpty()) {
            throw new EntryNotFoundException("Entry with id: " + id + " not found");
        } else {
            logger.info("Entry with id " + id + " updated by admin.");
            Entry entry = entryOptional.get();
            objectMapper.readerForUpdating(entry).readValue(objectMapper.writeValueAsString(updatedEntry2));
            entryService.createEntry(entry);
            return ResponseEntity.ok().build();
        }
    }
}
