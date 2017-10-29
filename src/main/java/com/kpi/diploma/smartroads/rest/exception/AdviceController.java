package com.kpi.diploma.smartroads.rest.exception;

import com.kpi.diploma.smartroads.model.exception.*;
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

    @ExceptionHandler(IncorrectInputDataException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectInputDataException(
            HttpServletRequest request, IncorrectInputDataException ex) {
        log.error("'handleIncorrectInputDataException' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getErrorMessage());
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectInviteKey.class)
    public ResponseEntity<ErrorMessage> handleIncorrectInviteUrl(
            HttpServletRequest request, IncorrectInviteKey ex) {
        log.error("'handleIncorrectInviteUrl' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getErrorMessage());
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ErrorMessage> handleEmailException(
            HttpServletRequest request, EmailException ex) {
        log.error("'handleEmailException' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getMessage());
        ex.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), ex.getClass().getCanonicalName());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageServiceException.class)
    public ResponseEntity<ErrorMessage> handleImageServiceExceptions(
            HttpServletRequest request, ImageServiceException ex) {
        log.error("'handleImageServiceExceptions' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    @Order
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOtherExceptions(
            HttpServletRequest request, Exception ex) {
        log.error("'handleOtherExceptions' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getMessage());
        ex.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
