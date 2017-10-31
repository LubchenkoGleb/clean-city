package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;
import lombok.Data;

@Data
public class IncorrectInviteKey extends RuntimeException {

    private ErrorMessage errorMessage;

    public IncorrectInviteKey(String message) {
        super(message);
        this.errorMessage = new ErrorMessage(message, IncorrectInviteKey.class.getCanonicalName());
    }
}
