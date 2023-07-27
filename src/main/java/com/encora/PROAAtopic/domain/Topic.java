package com.encora.PROAAtopic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "TOPICS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer userId;

    @OneToMany(mappedBy = "topic")
    private Collection<Resource> resources;
}
