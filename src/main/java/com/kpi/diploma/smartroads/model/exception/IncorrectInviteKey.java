package com.kpi.diploma.smartroads.model.exception;

import lombok.Data;

@Data
public class IncorrectInviteKey extends RuntimeException {

    private ErrorMessage errorMessage;

    public IncorrectInviteKey(String message) {
        super(message);
        this.errorMessage = new ErrorMessage(message, IncorrectInviteKey.class.getCanonicalName());
    }
}
