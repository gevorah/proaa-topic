package com.encora.proaatopic.domain;

import com.encora.proaatopic.dto.TopicDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "TOPICS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedNativeQuery(name = "top_ten",
        query = "SELECT t.topic_id as id, t.name as name, COUNT(r.topic_id) as resources\n" +
        "FROM TOPICS t\n" +
        "LEFT JOIN RESOURCES r ON t.topic_id = r.topic_id\n" +
        "GROUP BY t.topic_id, t.name, t.user_id\n" +
        "ORDER BY resources DESC\n" +
        "LIMIT 10;",
        resultSetMapping = "topic_dto"
)
@SqlResultSetMapping(
        name = "topic_dto",
        classes = @ConstructorResult(
                targetClass = TopicDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "resources", type = Integer.class)
                }
        )
)
public class Topic {
    @Id
    @GeneratedValue
    @Column(name = "topic_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @OneToMany(mappedBy = "topic", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resource> resources;

    public Topic(String name, Integer userId) {
        this.name = name;
        this.userId = userId;
        this.resources = new ArrayList<>();
    }

    public boolean addResource(Resource resource){
        return this.resources.add(resource);
    }
}
