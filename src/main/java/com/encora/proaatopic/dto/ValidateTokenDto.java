package com.encora.proaatopic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ValidateTokenDto {
    private String _id;
    private Long iat;
    private Long exp;
}
