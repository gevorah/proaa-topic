package com.encora.proaatopic.repositories;

import com.encora.proaatopic.domain.Topic;
import com.encora.proaatopic.dto.TopicTopDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query(value = "SELECT new com.encora.proaatopic.dto.TopicDto(t.id, t.name, COUNT(r.id))\n" +
            "FROM Topic t\n" +
            "LEFT JOIN Resource r ON t.id = r.topic.id\n" +
            "GROUP BY t.id, t.name, t.userId\n" +
            "ORDER BY COUNT(r.id) DESC"
    )
    List<TopicTopDto> findTopicsOrderedByResources(Pageable pageable);

    @Query(value = "SELECT t FROM Topic t WHERE t.userId = :userId")
    List<Topic> findTopicsByUserId(@Param("userId") String userId);
}
