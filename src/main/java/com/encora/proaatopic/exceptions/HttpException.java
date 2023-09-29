package com.encora.proaatopic.exceptions;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
    private final Integer status;

    public HttpException(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
