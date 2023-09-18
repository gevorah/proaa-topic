package com.encora.proaatopic.repositories;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query(name = "top_ten", nativeQuery = true)
    List<TopicDto> topTen();
}
