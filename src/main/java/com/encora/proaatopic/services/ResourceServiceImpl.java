package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public List<Resource> resourcesByOwner(String userId) {
        return resourceRepository.findResourcesByUserId(userId);
    }
}
