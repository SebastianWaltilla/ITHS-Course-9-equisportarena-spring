package com.contestmodule.contest.service;

import com.contestmodule.contest.dto.ContestInfoForUserDto;
import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.repository.ContestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContestService {

    private ContestRepository contestRepository;

    public ContestService(ContestRepository contestRepository){this.contestRepository = contestRepository;}


    public Contest createContest(Contest contest) {
        return contestRepository.save(contest) ;
    }

    public Iterable<Contest> findAllContests(){ return contestRepository.findAll();}



    //TODO Vid ett senare tillfälle lägg till en extra check så man inte kan få ut tävlingar som inte är aktiva.
    public Optional<Contest> findContestByID(Long id) { return contestRepository.findById(id);
    }

    public void deleteContest(Long id) {
        contestRepository.deleteById(id);
    }

    public List<ContestInfoForUserDto> findAllContestsForUser() {
       return contestRepository.findAllActiveContests(LocalDate.now(), LocalDate.now());
    }

    public Optional<ContestInfoForUserDto> findContestInfoForUserByID(Long id) {
        return contestRepository.findContestInfoForUserById(id, LocalDate.now());
    }
}
