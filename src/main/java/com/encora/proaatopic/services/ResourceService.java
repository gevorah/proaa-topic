package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> resourcesByOwner(String userId);
}
