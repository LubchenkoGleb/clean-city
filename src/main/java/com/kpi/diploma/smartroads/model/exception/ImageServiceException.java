package com.kpi.diploma.smartroads.model.exception;

import lombok.Data;

@Data
public class ImageServiceException extends RuntimeException {

    private ErrorMessage errorMessage;

    public ImageServiceException(String message) {
        super(message);
        this.errorMessage = new ErrorMessage(message, IncorrectInviteKey.class.getCanonicalName());
    }
}
