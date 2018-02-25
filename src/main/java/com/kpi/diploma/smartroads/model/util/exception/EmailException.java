package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class EmailException extends BaseRuntimeException {


    public EmailException(String message) {
        super(new ErrorMessage(message));
    }
}
