package com.contestmodule.contest.repository;

import com.contestmodule.contest.entity.Entry;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository <Entry, Long> {

    //Iterable<Entry> findAllByContestId(Long contestId);

    //Iterable<Entry> findByUser_id(Long userId);

}
