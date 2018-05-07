package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class GoogleBouldRouteException extends BaseRuntimeException {

    public GoogleBouldRouteException(String message) {
        super(new ErrorMessage(message));
    }

}
