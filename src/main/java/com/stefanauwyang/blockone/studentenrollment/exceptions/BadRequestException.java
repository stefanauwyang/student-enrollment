package com.stefanauwyang.blockone.studentenrollment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable ex) {
        super(ex);
    }

    public BadRequestException(String message, Throwable ex) {
        super(message, ex);
    }

}
