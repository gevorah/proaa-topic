package com.encora.PROAAtopic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "RESOURCES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Resource {
    @Id
    @GeneratedValue
    private Integer id;
    private String descriptionName;
    private String url;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}
