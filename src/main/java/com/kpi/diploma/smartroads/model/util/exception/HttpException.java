package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class HttpException extends BaseRuntimeException {

    public HttpException(String message) {
        super(new ErrorMessage(message));
    }

}
