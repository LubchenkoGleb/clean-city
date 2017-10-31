package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;
import lombok.Data;

@Data
public class ImageServiceException extends RuntimeException {

    private ErrorMessage errorMessage;

    public ImageServiceException(String message) {
        super(message);
        this.errorMessage = new ErrorMessage(message, IncorrectInviteKey.class.getCanonicalName());
    }
}
