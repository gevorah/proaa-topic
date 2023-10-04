package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.dto.ResourceDto;

import java.util.List;

public interface ResourceService {
    List<Resource> resourcesByOwner(String userId);

    Resource addResource(Resource resource, Integer topicId);
}
