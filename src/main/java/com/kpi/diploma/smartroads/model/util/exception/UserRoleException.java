package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class UserRoleException extends BaseRuntimeException {

    public UserRoleException(String errorMessage) {
        super(new ErrorMessage(errorMessage));
    }
}
