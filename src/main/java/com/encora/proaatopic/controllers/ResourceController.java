package com.encora.proaatopic.controllers;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.exceptions.HttpException;
import com.encora.proaatopic.services.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin({"http://127.0.0.1:5173", "http://localhost:5173"})
@RestController
@RequestMapping(path = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping()
    public ResponseEntity<List<Resource>> resourcesByOwner() {
        log.debug("Running resources by owner endpoint");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            List<Resource> resources = resourceService.resourcesByOwner(userId);
            log.info(resources.size() + " resources retrieved by " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(resources);
        } catch (Exception e) {
            log.error("Unable to access resources data with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
