package com.encora.proaatopic.configs;

import com.encora.proaatopic.dto.HttpExceptionDto;
import com.encora.proaatopic.exceptions.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<HttpExceptionDto> handleHttpException(HttpException e) {
        HttpExceptionDto dto = new HttpExceptionDto(e.getStatus(), e.getMessage());
        return ResponseEntity.status(dto.getStatus()).body(dto);
    }
}
