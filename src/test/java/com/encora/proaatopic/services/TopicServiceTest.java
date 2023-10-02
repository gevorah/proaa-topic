package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicTopDto;
import com.encora.proaatopic.repositories.TopicRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TopicServiceTest {
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    public List<Topic> topics;

    @BeforeAll
    void beforeAll() {
        topicService = new TopicServiceImpl();
        topics = new ArrayList<>();
        topics.add(new Topic("Unit testing 1", "1"));
        topics.add(new Topic("Unit testing 2", "1"));
        topics.add(new Topic("Unit testing 3", "1"));
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TopTen {
        public List<TopicTopDto> topTen = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,10);

        @BeforeAll
        void beforeAll() {
            topTen.add(new TopicTopDto(1, "Unit testing 1", 3L));
            topTen.add(new TopicTopDto(2, "Unit testing 2", 1L));
            topTen.add(new TopicTopDto(3, "Unit testing 3",  0L));
        }

        @Test
        void topTen_when_called_should_return_topic_list() {
            when(topicRepository.findTopicsOrderedByResources(pageable)).thenReturn(topTen);
            List<TopicTopDto> topics = topicService.topTen();
            assertEquals(3, topics.size());
            verify(topicRepository).findTopicsOrderedByResources(pageable);
        }

        @Test
        void topTen_when_no_topics_should_return_empty() {
            when(topicRepository.findTopicsOrderedByResources(pageable)).thenReturn(Collections.emptyList());
            List<TopicTopDto> topics = topicRepository.findTopicsOrderedByResources(pageable);
            assertTrue(topics.isEmpty());
            verify(topicRepository).findTopicsOrderedByResources(pageable);
        }

        @Test
        void topTen_when_called_should_return_sorted_list() {
            when(topicRepository.findTopicsOrderedByResources(pageable)).thenReturn(topTen);
            List<TopicTopDto> topics = topicRepository.findTopicsOrderedByResources(pageable);
            assertThat(topics).isSortedAccordingTo((o1, o2) -> o2.getResources().compareTo(o1.getResources()));
            verify(topicRepository).findTopicsOrderedByResources(pageable);
        }
    }

    @Nested
    class TopicsByOwner {
        @Test
        void when_called_with_valid_userId_should_return_list() throws Exception {
            String userId = "1";
            when(topicRepository.findTopicsByUserId(userId)).thenReturn(topics);
            List<Topic> topics = topicService.topicsByOwner(userId);
            assertEquals(3, topics.size());
            verify(topicRepository).findTopicsByUserId(userId);
        }

        @Test
        void when_called_with_invalid_userId_should_return_empty() throws Exception {
            String userId = "0";
            when(topicRepository.findTopicsByUserId(userId)).thenReturn(Collections.emptyList());
            List<Topic> topics = topicService.topicsByOwner(userId);
            assertTrue(topics.isEmpty());
            verify(topicRepository).findTopicsByUserId(userId);
        }

        @Test
        void when_called_with_null_userId_should_return_empty() throws Exception {
            when(topicRepository.findTopicsByUserId(null)).thenReturn(Collections.emptyList());
            List<Topic> topics = topicService.topicsByOwner(null);
            assertTrue(topics.isEmpty());
            verify(topicRepository).findTopicsByUserId(null);
        }
    }

}
