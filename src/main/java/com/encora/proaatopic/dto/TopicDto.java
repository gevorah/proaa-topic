package com.encora.proaatopic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TopicDto {
    Integer id;
    String name;
    Long resources;
}
