package com.stefanauwyang.blockone.studentenrollment.exceptions;

public class CreditException extends RuntimeException {

    public CreditException(String message) {
        super(message);
    }

    public CreditException(Throwable ex) {
        super(ex);
    }

    public CreditException(String message, Throwable ex) {
        super(message, ex);
    }

}
