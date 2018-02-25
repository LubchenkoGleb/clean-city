package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;
import lombok.Data;


@Data
public class BaseRuntimeException extends RuntimeException {
    private ErrorMessage errorMessage;

    public BaseRuntimeException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
