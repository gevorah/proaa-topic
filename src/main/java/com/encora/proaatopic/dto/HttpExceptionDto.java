package com.encora.proaatopic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpExceptionDto {
    private Integer status;
    private String message;
}
