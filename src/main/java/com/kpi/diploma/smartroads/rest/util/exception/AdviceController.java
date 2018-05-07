package com.kpi.diploma.smartroads.rest.util.exception;

import com.kpi.diploma.smartroads.model.util.exception.*;
import com.kpi.diploma.smartroads.model.util.data.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class AdviceController {

//    @ExceptionHandler(IncorrectInputDataException.class)
//    public ResponseEntity<ErrorMessage> handleIncorrectInputDataException(
//            HttpServletRequest request, IncorrectInputDataException ex) {
//        log.error("'handleIncorrectInputDataException' invoked for imageUrl'{}'", request.getRequestURL());
//        log.error("'exceptionMessage={}'", ex.getErrorMessage());
//        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(IncorrectInviteKey.class)
//    public ResponseEntity<ErrorMessage> handleIncorrectInviteUrl(
//            HttpServletRequest request, IncorrectInviteKey ex) {
//        log.error("'handleIncorrectInviteUrl' invoked for imageUrl'{}'", request.getRequestURL());
//        log.error("'exceptionMessage={}'", ex.getErrorMessage());
//        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(EmailException.class)
//    public ResponseEntity<ErrorMessage> handleEmailException(
//            HttpServletRequest request, EmailException ex) {
//        log.error("'handleEmailException' invoked for imageUrl'{}'", request.getRequestURL());
//        log.error("'exceptionMessage={}'", ex.getMessage());
//        ex.printStackTrace();
//        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), ex.getClass().getCanonicalName());
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ErrorMessage> handleBaseRuntimeExceptions(BaseRuntimeException ex) {
        ex.printStackTrace();
        log.error("'handleBaseRuntimeExceptions' invoked with params'{}'", ex.getErrorMessage().getMessage());

        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString());
        }
        log.error(stringBuilder.toString());

        ErrorMessage errorMessage = ex.getErrorMessage();
        errorMessage.setExceptionName(ex.getClass().getSimpleName());
        log.error("'errorMessage={}'", errorMessage);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Order
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOtherExceptions(
            HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        log.error("'handleOtherExceptions' invoked for request'{}'", request.getRequestURL());

        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString());
        }
        log.error(stringBuilder.toString());

        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
        errorMessage.setExceptionName(ex.getClass().getSimpleName());
        log.error("'errorMessage={}'", errorMessage);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
