package com.contestmodule.contest.controller;

import com.contestmodule.contest.kafka.KafKaProducerService;
import com.contestmodule.contest.service.EntryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/kafka")
@PermitAll
public class KafkaProducerController {
    private final KafKaProducerService producerService;
    Logger logger = LoggerFactory.getLogger(KafkaProducerController.class);

    @Autowired
    public KafkaProducerController(KafKaProducerService producerService)
    {
        this.producerService = producerService;
    }


    @ApiOperation(value = "Send message with kafka to another service with message service")
    @PostMapping(path = "/show")
    public void sendMessageToKafkaTopic()
    {
        logger.info("Send message with kafka to another service with message service");
        this.producerService.sendMessage("Sending from Equisportarena");
    }
}