package com.encora.proaatopic.controllers;

import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.services.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin({"http://127.0.0.1:5173", "http://localhost:5173"})
@RestController
@RequestMapping(path = "/topics")
@Slf4j
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping(path = "/top-ten", produces = MediaType.APPLICATION_JSON_VALUE)
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

}
