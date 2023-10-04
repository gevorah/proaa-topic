package com.encora.proaatopic.repositories;

import com.encora.proaatopic.domain.Resource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource("/application.properties")
public class ResourceRepositoryTest {
    @Autowired
    private ResourceRepository resourceRepository;

    @Nested
    class FindResourceByUserId {
        @Test
        void when_called_with_valid_userId_should_return_list() throws Exception {
            String userId = "1";
            List<Resource> resources = resourceRepository.findResourcesByUserId(userId);
            assertEquals(4, resources.size());
        }

        @Test
        void when_called_with_invalid_userId_should_return_empty() throws Exception {
            String userId = "0";
            List<Resource> resources = resourceRepository.findResourcesByUserId(userId);
            assertTrue(resources.isEmpty());
        }

        @Test
        void when_called_with_null_userId_should_return_empty() throws Exception {
            List<Resource> resources = resourceRepository.findResourcesByUserId(null);
            assertTrue(resources.isEmpty());
        }
    }
}
