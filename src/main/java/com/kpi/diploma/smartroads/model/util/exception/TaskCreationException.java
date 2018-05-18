package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class TaskCreationException extends BaseRuntimeException {

    public TaskCreationException(String message) {
        super(new ErrorMessage(message));
    }

}
