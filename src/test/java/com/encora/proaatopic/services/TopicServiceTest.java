package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
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
class TopicServiceTest {
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService = new TopicServiceImpl();

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TopTen {
        public List<TopicDto> topTen = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,10);

        @BeforeAll
        void beforeAll(){
            topTen.add(new TopicDto(1, "Unit testing 1", new Long(3)));
            topTen.add(new TopicDto(2, "Unit testing 2",  new Long(1)));
            topTen.add(new TopicDto(3, "Unit testing 3",  new Long(0)));
        }

        @Test
        void topTen_when_called_should_return_topic_list() {
            when(topicRepository.findTopicsOrderedByResources(pageable)).thenReturn(topTen);
            List<TopicDto> topics = topicService.topTen();
            assertEquals(3, topics.size());
            verify(topicRepository).findTopicsOrderedByResources(pageable);
        }

        @Test
        void topTen_when_no_topics_should_return_empty() {
            when(topicRepository.findTopicsOrderedByResources(pageable)).thenReturn(Collections.emptyList());
            List<TopicDto> topics = topicRepository.findTopicsOrderedByResources(pageable);
            assertTrue(topics.isEmpty());
            verify(topicRepository).findTopicsOrderedByResources(pageable);
        }

        @Test
        void topTen_when_called_should_return_sorted_list() {
            when(topicRepository.findTopicsOrderedByResources(pageable)).thenReturn(topTen);
            List<TopicDto> topics = topicRepository.findTopicsOrderedByResources(pageable);
            assertThat(topics).isSortedAccordingTo((o1, o2) -> o2.getResources().compareTo(o1.getResources()));
            verify(topicRepository).findTopicsOrderedByResources(pageable);
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class Topics {
        public List<Topic> topics = new ArrayList<>();

        @BeforeAll
        void beforeAll() {
            topics.add(new Topic("Unit testing 1", "1"));
            topics.add(new Topic("Unit testing 2", "2"));
            topics.add(new Topic("Unit testing 3", "3"));
        }

    }

}
