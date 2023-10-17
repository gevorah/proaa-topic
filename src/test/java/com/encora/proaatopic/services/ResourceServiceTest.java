package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Resource;
import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.repositories.ResourceRepository;
import com.encora.proaatopic.repositories.TopicRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResourceServiceTest {
    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private TopicRepository topicRepository;

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

    @Nested
    class CreateResource {
        @Test
        void when_called_with_resource_should_return_resource() throws Exception {
            Resource resource = new Resource("Resource", "Resource URL");

            when(topicRepository.findById(1)).thenReturn(Optional.of(new Topic("Topic", "1")));
            when(resourceRepository.save(ArgumentMatchers.any(Resource.class))).thenReturn(resource);

            assertEquals(resource, resourceService.addResource(resource, 1));
            verify(resourceRepository).save(resource);
        }

        @Test
        void when_called_without_existing_topic_should_throw_error() throws Exception {
            when(topicRepository.findById(1)).thenThrow(new NoSuchElementException());

            Exception exception = assertThrows(NoSuchElementException.class, () -> {
                resourceService.addResource(null, 1);
            });
            assertEquals(null, exception.getMessage());
            verify(topicRepository).findById(1);
        }

        @Test
        void when_called_without_resource_should_throw_error() throws Exception {
            when(topicRepository.findById(1)).thenReturn(Optional.of(new Topic("Topic", "1")));

            Exception exception = assertThrows(NullPointerException.class, () -> {
                resourceService.addResource(null, 1);
            });
            assertEquals(null, exception.getMessage());
            verify(resourceRepository, never()).save(null);
        }
    }

    @Nested
    class UpdateResource {
        @Test
        void when_called_with_resource_should_return_resource() throws Exception {
            Resource resource = new Resource(1, "Resource", "Resource URL", new Topic(1, "Topic", "1", null));

            when(resourceRepository.findById(resource.getId())).thenReturn(Optional.of(resource));
            when(topicRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(new Topic("Topic", "1")));
            when(resourceRepository.save(ArgumentMatchers.any(Resource.class))).thenReturn(resource);

            assertEquals(resource, resourceService.editResource(resource, 2));
            verify(resourceRepository).save(resource);
        }

        @Test
        void when_called_without_existing_topic_should_throw_error() throws Exception {
            Resource resource = new Resource(1, "Resource", "Resource URL", new Topic(1, "Topic", "1", null));

            when(resourceRepository.findById(resource.getId())).thenReturn(Optional.of(resource));
            when(topicRepository.findById(0)).thenThrow(new NoSuchElementException());

            Exception exception = assertThrows(NoSuchElementException.class, () -> {
                resourceService.editResource(resource, 0);
            });
            assertEquals(null, exception.getMessage());
            verify(resourceRepository).findById(resource.getId());
        }

        @Test
        void when_called_without_existing_resource_should_throw_error() throws Exception {
            Resource resource = new Resource(1, "Resource", "Resource URL", null);

            when(resourceRepository.findById(resource.getId())).thenThrow(new NoSuchElementException());

            Exception exception = assertThrows(NoSuchElementException.class, () -> {
                resourceService.editResource(resource, 1);
            });
            assertEquals(null, exception.getMessage());
            verify(resourceRepository).findById(resource.getId());
        }
    }
}
