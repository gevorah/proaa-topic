package com.encora.proaatopic.services;

import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.repositories.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void topTen_should_return_topic_list() {
        when(topicRepository.topTen()).thenReturn(this.buildTestingTopTen());
        List<TopicDto> topics = topicService.topTen();
        assertEquals(3, topics.size());
        verify(topicRepository).topTen();
    }

    @Test
    void topTen_should_handle_empty() {
        when(topicRepository.topTen()).thenReturn(Collections.emptyList());
        List<TopicDto> topics = topicRepository.topTen();
        assertTrue(topics.isEmpty());
        verify(topicRepository).topTen();
    }

    @Test
    void topTen_should_return_sorted_list() {
        when(topicRepository.topTen()).thenReturn(this.buildTestingTopTen());
        List<TopicDto> topics = topicRepository.topTen();
        assertThat(topics).isSortedAccordingTo((o1, o2) -> o2.getResources().compareTo(o1.getResources()));
        verify(topicRepository).topTen();
    }

    private List<TopicDto> buildTestingTopTen() {
        List<TopicDto> topics = new ArrayList<>();
        topics.add(new TopicDto(1, "Unit testing 1", 3));
        topics.add(new TopicDto(2, "Unit testing 2", 1));
        topics.add(new TopicDto(3, "Unit testing 3", 0));
        return topics;
    }
}
