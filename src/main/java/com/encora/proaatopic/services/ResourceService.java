package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> resourcesByOwner(String userId);

    Resource resourceByOwner(Integer id, String userId);

    Resource addResource(Resource resource, Integer topicId);

    Resource editResource(Resource resource, Integer topicId);
}
