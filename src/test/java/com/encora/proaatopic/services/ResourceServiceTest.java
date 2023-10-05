package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.repositories.ResourceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResourceServiceTest {
    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    private List<Resource> resources;

    @BeforeAll
    void beforeAll() {
        resourceService = new ResourceServiceImpl();
        resources = new ArrayList<>();

        resources.add(new Resource(1, "Unit testing 1", "", null));
        resources.add(new Resource(2, "Unit testing 2", "", null));
        resources.add(new Resource(3, "Unit testing 3", "", null));
    }

    @Nested
    class ResourcesByOwner {
        @Test
        void when_called_with_valid_userId_should_return_list() throws Exception {
            String userId = "1";
            when(resourceRepository.findResourcesByUserId(userId)).thenReturn(resources);
            List<Resource> resources = resourceService.resourcesByOwner(userId);
            assertEquals(3, resources.size());
            verify(resourceRepository).findResourcesByUserId(userId);
        }

        @Test
        void when_called_with_invalid_userId_should_return_empty() throws Exception {
            String userId = "0";
            when(resourceRepository.findResourcesByUserId(userId)).thenReturn(Collections.emptyList());
            List<Resource> resources = resourceService.resourcesByOwner(userId);
            assertTrue(resources.isEmpty());
            verify(resourceRepository).findResourcesByUserId(userId);
        }

        @Test
        void when_called_with_null_userId_should_return_empty() throws Exception {
            when(resourceRepository.findResourcesByUserId(null)).thenReturn(Collections.emptyList());
            List<Resource> resources = resourceService.resourcesByOwner(null);
            assertTrue(resources.isEmpty());
            verify(resourceRepository).findResourcesByUserId(null);
        }
    }
}
