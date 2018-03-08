package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class ResourseDoesntBelongToUserException extends BaseRuntimeException {

    public ResourseDoesntBelongToUserException(String message) {
        super(new ErrorMessage(message));
    }
}
