package com.encora.proaatopic.repositories;

import com.encora.proaatopic.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    @Query(value = "SELECT r\n" +
            "FROM Resource r\n" +
            "LEFT JOIN Topic t ON r.topic.id = t.id\n" +
            "WHERE t.userId = :userId")
    List<Resource> findResourcesByUserId(@Param("userId") String userId);
}