package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class ResourceNotFoundException extends BaseRuntimeException {
    public ResourceNotFoundException(String message) {
        super(new ErrorMessage(message));
    }
}
