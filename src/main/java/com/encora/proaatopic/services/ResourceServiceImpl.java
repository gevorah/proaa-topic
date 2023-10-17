package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.exceptions.HttpException;
import com.encora.proaatopic.repositories.ResourceRepository;
import com.encora.proaatopic.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private TopicRepository topicRepository;

    public List<Resource> resourcesByOwner(String userId) {
        return resourceRepository.findResourcesByUserId(userId);
    }

    public Resource resourceByOwner(Integer id, String userId) {
        Resource resource = resourceRepository.findById(id).orElseThrow();
        if(!resource.getTopic().getUserId().equalsIgnoreCase(userId)) {
            throw new HttpException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        return resource;
    }

    public Resource addResource(Resource resource, Integer topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        resource.setTopic(topic);
        topic.addResource(resource);
        return resourceRepository.save(resource);
    }

    public Resource editResource(Resource resource, Integer topicId) {
        Resource resourceFromDb = resourceRepository.findById(resource.getId()).orElseThrow();
        resourceFromDb.setDescriptionName(resource.getDescriptionName());
        resourceFromDb.setUrl(resource.getUrl());
        if(!resourceFromDb.getTopic().getId().equals(topicId)) {
            Topic topic = topicRepository.findById(topicId).orElseThrow();
            resourceFromDb.setTopic(topic);
            topic.addResource(resourceFromDb);
        }
        return resourceRepository.save(resourceFromDb);
    }
}
