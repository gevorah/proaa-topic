package com.encora.proaatopic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourceDto {
    String descriptionName;
    String url;
    Integer topicId;
}
