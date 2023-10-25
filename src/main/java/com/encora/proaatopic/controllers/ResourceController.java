package com.encora.proaatopic.controllers;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.dto.ResourceDto;
import com.encora.proaatopic.exceptions.HttpException;
import com.encora.proaatopic.services.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping(path = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @Operation(summary = "Get resources by owner", tags = "Resources")
    @ApiResponse( responseCode = "200", description = "Successfully retrieve resources")
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
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Get resource by id and owner", tags = "Resources")
    @Parameters(value = { @Parameter(name = "id", description = "Id of the resource to search and retrieve") })
    @ApiResponse( responseCode = "200", description = "Successfully retrieve resource")
    @GetMapping("/{id}")
    public ResponseEntity<Resource> resourceByOwner(@PathVariable Integer id) {
        log.debug("Running resources by owner endpoint");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        Resource resource = resourceService.resourceByOwner(id, userId);
        log.info(resource.getDescriptionName() + " resource retrieved by " + userId);
        return ResponseEntity.status(HttpStatus.OK).body(resource);
    }

    @Operation(summary = "Create resource", tags = "Resources")
    @ApiResponse( responseCode = "200", description = "Successfully create resource")
    @PostMapping()
    public ResponseEntity<Resource> createResource(@RequestBody ResourceDto resourceDto) {
        log.debug("Running create resource endpoint");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            Resource createResource = new Resource(resourceDto.getDescriptionName(), resourceDto.getUrl());
            Resource resource = resourceService.addResource(createResource, resourceDto.getTopicId());
            log.info(resource.getDescriptionName() + " resource created by " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            log.error("Unable to create resource with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Operation(summary = "Update resource", tags = "Resources")
    @Parameters(value = { @Parameter(name = "id", description = "Id of the resource to search and update") })
    @ApiResponse( responseCode = "200", description = "Successfully update resource")
    @PatchMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Integer id, @RequestBody ResourceDto resourceDto) {
        log.debug("Running edit resource endpoint");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            Resource updateResource = new Resource(id, resourceDto.getDescriptionName(), resourceDto.getUrl(), null);
            Resource resource = resourceService.editResource(updateResource, resourceDto.getTopicId());
            log.info(resource.getDescriptionName() + " resource edited by " + userId);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        } catch (Exception e) {
            log.error("Unable to edit resource with message: " + e.getMessage(), e);
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
