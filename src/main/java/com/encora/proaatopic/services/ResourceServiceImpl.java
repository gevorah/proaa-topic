package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.ResourceDto;
import com.encora.proaatopic.repositories.ResourceRepository;
import com.encora.proaatopic.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Resource addResource(Resource resource, Integer topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        resource.setTopic(topic);
        topic.addResource(resource);
        topicRepository.save(topic);
        return resourceRepository.save(resource);
    }
}
