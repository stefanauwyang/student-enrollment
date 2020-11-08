package com.stefanauwyang.blockone.studentenrollment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable ex) {
        super(ex);
    }

    public ValidationException(String message, Throwable ex) {
        super(message, ex);
    }

}
