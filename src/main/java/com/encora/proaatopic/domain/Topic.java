package com.encora.proaatopic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "Topics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue(generator = "topic_generator")
    @GenericGenerator(name = "topic_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "topic_sequence"),
                    @Parameter(name = "initial_value", value = "14"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "topic_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resource> resources;

    public Topic(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.resources = new ArrayList<>();
    }

    public boolean addResource(Resource resource) {
        return this.resources.add(resource);
    }
}
