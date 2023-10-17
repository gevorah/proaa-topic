package com.encora.proaatopic.configs;

import com.encora.proaatopic.dto.HttpExceptionDto;
import com.encora.proaatopic.exceptions.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<HttpExceptionDto> handleHttpException(HttpException e) {
        HttpExceptionDto dto = new HttpExceptionDto(e.getStatus().value(), e.getMessage());
        return ResponseEntity.status(dto.getStatus()).body(dto);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<HttpExceptionDto> handleNoSuchElementException(NoSuchElementException e) {
        HttpExceptionDto dto = new HttpExceptionDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(dto.getStatus()).body(dto);
    }
}
