package com.encora.proaatopic.services;

import com.encora.proaatopic.dto.TopicDto;
import com.encora.proaatopic.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<TopicDto> topTen() {
        return topicRepository.topTen();
    }

}
