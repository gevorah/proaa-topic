package com.encora.proaatopic.services;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;

import java.util.List;

public interface TopicService {
    List<TopicDto> topTen();

}
