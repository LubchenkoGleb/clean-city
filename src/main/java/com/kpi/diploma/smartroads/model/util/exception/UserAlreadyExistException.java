package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class UserAlreadyExistException extends BaseRuntimeException {

    public UserAlreadyExistException(String message) {
        super(new ErrorMessage(message));
    }
}
