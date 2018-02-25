package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;
import lombok.Data;

@Data
public class IncorrectInputDataException extends BaseRuntimeException {

    public IncorrectInputDataException(String message) {
        super(new ErrorMessage(message));
    }
}
