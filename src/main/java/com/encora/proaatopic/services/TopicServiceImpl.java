package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicTopDto;
import com.encora.proaatopic.exceptions.HttpException;
import com.encora.proaatopic.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<TopicTopDto> topTen() {
        Pageable pageable = PageRequest.of(0,10);
        return topicRepository.findTopicsOrderedByResources(pageable);
    }

    public List<Topic> topicsByOwner(String userId) {
        return topicRepository.findTopicsByUserId(userId);
    }

    public Topic topicByOwner(Integer id, String userId) {
        Topic topic = topicRepository.findById(id).orElseThrow();
        if (!topic.getUserId().equalsIgnoreCase(userId)) {
            throw new HttpException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        return topic;
    }

    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic editTopic(Topic topic) {
        Topic topicFromDb = topicRepository.findById(topic.getId()).orElseThrow();
        if(!topicFromDb.getUserId().equalsIgnoreCase(topic.getUserId())) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        topicFromDb.setName(topic.getName());
        return topicRepository.save(topicFromDb);
    }
}
