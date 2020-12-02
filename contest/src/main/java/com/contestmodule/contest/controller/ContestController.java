package com.contestmodule.contest.controller;

import com.contestmodule.contest.entity.Contest;
import com.contestmodule.contest.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contest")
public class ContestController {

    Logger logger = LoggerFactory.getLogger(ContestService.class);

    private ContestService contestService;

    public ContestController(ContestService contestService){
        this.contestService = contestService;
    }

@PostMapping("/create")
    public Contest createContest(@RequestBody Contest contest) {
        logger.info("createContest() was called with contestname: " + contest.getName());
        return contestService.createContest(contest);
}

@GetMapping("/findall")
    public Iterable<Contest> findAllContests(){
        return contestService.findAllContests();
}

@GetMapping("/id/{id}")
    public Optional<Contest> findContestByID(@PathVariable Long id) {
        return contestService.findContestByID(id);
    }

@GetMapping("/active")
    public Iterable<Contest> findAllActiveContests(){
        return contestService.findAllActiveContests();
}

@DeleteMapping("/delete/{id}")
    public void deleteContest(@PathVariable Long id ){
        contestService.deleteContest(id);
}

@GetMapping("/403")
    public String error403(){
        return "403";
}


}
