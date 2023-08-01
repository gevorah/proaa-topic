package com.encora.proaatopic.repositories;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    @Test
    void topTen_should_return_topic_list() {
        List<TopicDto> topics = topicRepository.topTen();
        assertEquals(10, topics.size());
    }

    @Test
    void topTen_should_handle_empty() {
        topicRepository.deleteAll();
        List<TopicDto> topics = topicRepository.topTen();
        assertTrue(topics.isEmpty());
    }

    @Test
    void topTen_should_return_sorted_list() {
        List<TopicDto> topics = topicRepository.topTen();
        assertThat(topics).isSortedAccordingTo((o1, o2) -> o2.getResources().compareTo(o1.getResources()));
    }
}
