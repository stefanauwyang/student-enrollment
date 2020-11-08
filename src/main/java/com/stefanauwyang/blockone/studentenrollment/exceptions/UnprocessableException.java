package com.stefanauwyang.blockone.studentenrollment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableException extends RuntimeException {

    public UnprocessableException(String message) {
        super(message);
    }

    public UnprocessableException(Throwable ex) {
        super(ex);
    }

    public UnprocessableException(String message, Throwable ex) {
        super(message, ex);
    }

}
