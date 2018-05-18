package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;

public class CompanyEndpoinsNotSet extends BaseRuntimeException {

    public CompanyEndpoinsNotSet(String message) {
        super(new ErrorMessage(message));
    }

}
