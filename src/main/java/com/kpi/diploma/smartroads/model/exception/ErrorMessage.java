package com.kpi.diploma.smartroads.model.exception;

import lombok.Data;

@Data
public class ErrorMessage {
    private boolean isException = true;
    private String message;
    private String exceptionName;

    public ErrorMessage(String message, String exceptionName) {
        this.message = message;
        this.exceptionName = exceptionName;
    }
}
