package com.encora.proaatopic.controllers;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.dto.TopicTopDto;
import com.encora.proaatopic.exceptions.HttpException;
import com.encora.proaatopic.services.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<List<TopicTopDto>> topTen() {
        log.debug("Running top ten topics endpoint");
        try {
            List<TopicTopDto> topics = topicService.topTen();
            log.info("Top " + topics.size() + " topics");
            return ResponseEntity.status(HttpStatus.OK).body(topics);
        } catch (Exception e) {
            log.error("Unable to access top ten topics data with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<Topic>> topicsByOwner() {
        log.debug("Running topics by owner endpoint");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            List<Topic> topics = topicService.topicsByOwner(userId);
            log.info(topics.size() + " topics retrieved by " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(topics);
        } catch (Exception e) {
            log.error("Unable to access topics data with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Topic> createTopic(@RequestBody TopicDto topicDto) {
        log.debug("Running create topic endpoint");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            Topic topic = topicService.addTopic(new Topic(topicDto.getName(), userId));
            log.info(topic.getName() + " topic created by " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(topic);
        } catch (Exception e) {
            log.error("Unable to create topic with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Integer id, @RequestBody TopicDto topicDto) {
        log.debug("Running create topic endpoint");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            Topic topic = topicService.editTopic(new Topic(id, topicDto.getName(), userId, null));
            log.info(topic.getName() + " topic updated by " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(topic);
        } catch (Exception e) {
            log.error("Unable to create topic with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

}
