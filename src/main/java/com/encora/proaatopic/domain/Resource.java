package com.encora.proaatopic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Resources")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resource {
    @Id
    @GeneratedValue
    @Column(name = "resource_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "description_name", nullable = false)
    private String descriptionName;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}
