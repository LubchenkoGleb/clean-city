package com.kpi.diploma.smartroads.model.util.exception;

import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;
import lombok.Data;

@Data
public class ImageServiceException extends BaseRuntimeException {

    public ImageServiceException(String message) {
        super(new ErrorMessage(message));
    }
}
