package com.contestmodule.contest.repository;

import com.contestmodule.contest.dto.AdminEntryDto;
import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.dto.EntryWithContestNameDto;
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

    @Query(value = "Select new com.contestmodule.contest.dto.AdminEntryDto(" +
            "e.userId, " +
            "e.contest.id, " +
            "e.videolink, " +
            "e.userComment, " +
            "e.horseName, " +
            "e.adminComment, " +
            "e.hasPaid, " +
            "e.score, " +
            "e.submissionDate) " +
            "from Entry e where e.contest.id = :contestId")
    Iterable<AdminEntryDto> findAllByContestId(Long contestId);

    @Query(value = "SELECT e from Entry e where e.userId = :userId AND e.contest.id = :contestId")
    Optional<Entry> findByUserIdAndContestId(Long userId, Long contestId);

    @Query(value = "SELECT new com.contestmodule.contest.dto.EntryWithContestNameDto(" +
            "e.userId, " +
            "e.contest.id, " +
            "e.videolink, " +
            "e.userComment, " +
            "e.horseName, " +
            "e.contest.name) " +
            "from Entry e where e.userId = :userId")
    Iterable<EntryWithContestNameDto> findEntriesByUserId(Long userId);
}
