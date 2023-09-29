package com.encora.proaatopic.services;

import com.encora.proaatopic.dto.ValidateTokenDto;

public interface AuthService {
    ValidateTokenDto validateToken(String token);
}
