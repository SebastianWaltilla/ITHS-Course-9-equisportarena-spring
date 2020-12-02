package com.contestmodule.contest.service;

import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.repository.EntryRepository;
import org.springframework.stereotype.Service;

@Service
public class EntryService {

    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository){
        this.entryRepository = entryRepository;
    }

    public Entry createEntry(Entry entry){
        return entryRepository.save(entry);
    }

}
