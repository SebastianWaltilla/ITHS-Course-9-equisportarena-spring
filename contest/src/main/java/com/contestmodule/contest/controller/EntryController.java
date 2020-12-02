package com.contestmodule.contest.controller;

import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.service.EntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entry")
public class EntryController {

    Logger logger = LoggerFactory.getLogger(EntryService.class);

    private EntryService entryService;

    public EntryController(EntryService entryService){
        this.entryService = entryService;
    }

    @PostMapping("/create")
        public Entry createEntry(@RequestBody Entry entry){
        logger.info("Entry created with id" + entry.getId());
        return entryService.createEntry(entry);
    }



}
