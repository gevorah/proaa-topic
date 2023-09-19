package com.encora.proaatopic.controllers;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.services.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin({"http://127.0.0.1:5173", "http://localhost:5173"})
@RestController
@RequestMapping(path = "/topics", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping(path = "/top-ten")
    public ResponseEntity<List<TopicDto>> topTen(){
        log.debug("Running top ten topics endpoint");
        try {
            List<TopicDto> topics = topicService.topTen();
            log.info("Top " + topics.size() + " topics");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(topics);
        } catch (Exception e){
            log.error("Unable to access top ten topics data with message: " + e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Topic>> topicsByOwner(@RequestParam("userId") String userId){
        log.debug("Running topics by owner endpoint");
        try {
            List<Topic> topics = topicService.topicsByOwner(userId);
            log.info(topics.size() + " topics retrieved by " + userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(topics);
        } catch (Exception e){
            log.error("Unable to access topics data with message: " + e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
