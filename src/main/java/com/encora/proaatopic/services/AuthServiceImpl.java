package com.encora.proaatopic.services;

import com.encora.proaatopic.dto.HttpExceptionDto;
import com.encora.proaatopic.dto.ValidateTokenDto;
import com.encora.proaatopic.exceptions.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private WebClient webClient;

    public ValidateTokenDto validateToken(String token) {
        return webClient.get()
                .uri("/verify")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(HttpExceptionDto.class).flatMap(e ->
                                Mono.error(new HttpException(HttpStatus.resolve(e.getStatus()), e.getMessage()))
                        )
                )
                .bodyToMono(ValidateTokenDto.class)
                .block();
    }
}
