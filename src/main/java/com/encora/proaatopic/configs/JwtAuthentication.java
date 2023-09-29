package com.encora.proaatopic.configs;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthentication extends AbstractAuthenticationToken {
    private String userId;
    public JwtAuthentication(String userId) {
        super(null);
        this.userId = userId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return userId;
    }
}
