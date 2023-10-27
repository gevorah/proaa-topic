package com.encora.proaatopic.controllers;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.dto.TopicTopDto;
import com.encora.proaatopic.exceptions.HttpException;
import com.encora.proaatopic.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Get top ten topics with the most resources", tags = "Topics", responses = {@ApiResponse(responseCode = "200", description = "Successfully retrieve top ten topics")})
    @GetMapping(path = "/top-ten")
    public ResponseEntity<List<TopicTopDto>> topTen() {
        log.debug("Running top ten topics endpoint");
        try {
            List<TopicTopDto> topics = topicService.topTen();
            log.info("Top " + topics.size() + " topics");
            return ResponseEntity.status(HttpStatus.OK).body(topics);
        } catch (Exception e) {
            log.error("Unable to access top ten topics data with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Get topics by owner", tags = "Topics", responses = {@ApiResponse(responseCode = "200", description = "Successfully retrieve topics")}, security = {@SecurityRequirement(name = "Authorization")})
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
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Get topic by id and owner", tags = "Topics", parameters = {@Parameter(name = "id", description = "Id of the topic to search and retrieve")}, responses = {@ApiResponse(responseCode = "200", description = "Successfully retrieve topic")}, security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/{id}")
    public ResponseEntity<Topic> topicByOwner(@PathVariable Integer id) {
        log.debug("Running topic by owner endpoint");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        Topic topic = topicService.topicByOwner(id, userId);
        log.info(topic.getName() + " topic retrieved by " + userId);
        return ResponseEntity.status(HttpStatus.OK).body(topic);
    }

    @Operation(summary = "Create topic", tags = "Topics", responses = {@ApiResponse(responseCode = "200", description = "Successfully create topic")}, security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping()
    public ResponseEntity<Topic> createTopic(@RequestBody TopicDto topicDto) {
        log.debug("Running create topic endpoint");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            Topic createTopic = new Topic(topicDto.getName(), userId);
            Topic topic = topicService.addTopic(createTopic);
            log.info(topic.getName() + " topic created by " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(topic);
        } catch (Exception e) {
            log.error("Unable to create topic with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Update topic", tags = "Topics", parameters = {@Parameter(name = "id", description = "Id of the topic to search and update")}, responses = {@ApiResponse(responseCode = "200", description = "Successfully update topic")}, security = {@SecurityRequirement(name = "Authorization")})
    @PatchMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Integer id, @RequestBody TopicDto topicDto) {
        log.debug("Running update topic endpoint");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        Topic updateTopic = new Topic(id, topicDto.getName(), userId, null);
        Topic topic = topicService.editTopic(updateTopic);
        log.info(topic.getName() + " topic updated by " + userId);
        return ResponseEntity.status(HttpStatus.OK).body(topic);
    }

}
