package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class TaskException extends BaseRuntimeException {

    public TaskException(String message) {
        super(new ErrorMessage(message));
    }

}
