package com.kpi.diploma.smartroads.model.exception;

import lombok.Data;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;

@Data
public class IncorrectInputDataException extends RuntimeException {

    private ErrorMessage errorMessage;

    public IncorrectInputDataException(String message) {
        super(message);
        this.errorMessage = new ErrorMessage(message, IncorrectInviteUrl.class.getCanonicalName());
    }
}
