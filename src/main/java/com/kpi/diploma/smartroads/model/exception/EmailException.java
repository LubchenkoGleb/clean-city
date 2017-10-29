package com.kpi.diploma.smartroads.model.exception;

public class EmailException extends RuntimeException {

    private ErrorMessage errorMessage;

    public EmailException(String message) {
        super(message);
        this.errorMessage = new ErrorMessage(message, IncorrectInviteKey.class.getCanonicalName());
    }
}
