package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicTopDto;

import java.util.List;

public interface TopicService {
    List<TopicTopDto> topTen();

    List<Topic> topicsByOwner(String userId);

    Topic topicByOwner(Integer id, String userId);

    Topic addTopic(Topic topic);

    Topic editTopic(Topic topic);

}
