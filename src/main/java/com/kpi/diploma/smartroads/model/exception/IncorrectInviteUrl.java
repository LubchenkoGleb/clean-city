package com.kpi.diploma.smartroads.model.exception;

import lombok.Data;

@Data
public class IncorrectInviteUrl extends RuntimeException {

    private ErrorMessage errorMessage;

    public IncorrectInviteUrl(String message) {
        super(message);
        this.errorMessage = new ErrorMessage(message, IncorrectInviteUrl.class.getCanonicalName());
    }
}
