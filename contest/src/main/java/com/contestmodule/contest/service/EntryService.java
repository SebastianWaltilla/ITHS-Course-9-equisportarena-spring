package com.contestmodule.contest.service;

import com.contestmodule.contest.Exceptions.UserAlreadyInContestException;
import com.contestmodule.contest.dto.AdminEntryDto;
import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.dto.EntryWithContestNameDto;
import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.entity.User;
import com.contestmodule.contest.repository.EntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntryService {

    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository){
        this.entryRepository = entryRepository;
    }

    // Is this ok? or would it be better in an other place
    public Entry createEntry(Entry entry){
         return entryRepository.save(entry);
    }

    public Iterable<Entry> findAllEntries() {
        return entryRepository.findAll();
    }

    public Iterable<AdminEntryDto> findAllEntriesByContestId(Long id) {
        return entryRepository.findAllByContestId(id);
    }

    public Optional<Entry> findEntryById(Long id) {
        return entryRepository.findById(id);
    }
    public Optional<Entry> findEntryByUserId(Long userId, Long contestId) {
        return entryRepository.findByUserIdAndContestId(userId, contestId);
    }
    public Optional<Entry> findEntryByUserIdAndContestId(Long userId, Long contestId) {
        return entryRepository.findByUserIdAndContestId(userId, contestId);
    }

    public Optional<Entry> findEntryBy(Long id) {
        return entryRepository.findById(id);
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

    public Iterable<EntryWithContestNameDto> findAllEntriesByUserId(Long userId) {
        return entryRepository.findEntriesByUserId(userId);
    }
}
