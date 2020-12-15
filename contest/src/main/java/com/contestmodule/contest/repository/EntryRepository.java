package com.contestmodule.contest.repository;

import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.entity.Entry;
import com.contestmodule.contest.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EntryRepository extends CrudRepository <Entry, Long> {

    Iterable<Entry> findAllByContestId(Long contestId);

    @Query(value = "SELECT e from Entry e where e.userId = :userId AND e.contest.id = :contestId")
    Optional<Entry> findByUserIdAndContestId(Long userId, Long contestId);

}
