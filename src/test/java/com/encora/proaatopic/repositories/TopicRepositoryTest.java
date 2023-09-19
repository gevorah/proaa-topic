package com.encora.proaatopic.repositories;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource("/application.properties")
class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Nested
    class FindTopicsOrderedByResources {
        Pageable pageable = PageRequest.of(0,10);

        @Test
        void when_called_should_return_topic_list() {
            List<TopicDto> topics = topicRepository.findTopicsOrderedByResources(pageable);
            assertEquals(10, topics.size());
        }

        @Test
        void when_no_topics_should_return_empty() {
            topicRepository.deleteAll();
            List<TopicDto> topics = topicRepository.findTopicsOrderedByResources(pageable);
            assertTrue(topics.isEmpty());
        }

        @Test
        void when_called_should_return_sorted_list() {
            List<TopicDto> topics = topicRepository.findTopicsOrderedByResources(pageable);
            assertThat(topics).isSortedAccordingTo((o1, o2) -> o2.getResources().compareTo(o1.getResources()));
        }
    }

    @Nested
    class FindTopicsByUserId {
        @Test
        void when_called_with_valid_userId_should_return_list() throws Exception {
            String userId = "1";
            List<Topic> topics = topicRepository.findTopicsByUserId(userId);
            assertEquals(6, topics.size());
        }

        @Test
        void when_called_with_invalid_userId_should_return_empty() throws Exception {
            String userId = "0";
            List<Topic> topics = topicRepository.findTopicsByUserId(userId);
            assertTrue(topics.isEmpty());
        }

        @Test
        void when_called_with_null_userId_should_return_empty() throws Exception {
            List<Topic> topics = topicRepository.findTopicsByUserId(null);
            assertTrue(topics.isEmpty());
        }
    }


}
