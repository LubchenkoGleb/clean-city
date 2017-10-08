package com.kpi.diploma.smartroads.rest.exception;

import com.kpi.diploma.smartroads.model.exception.ErrorMessage;
import com.kpi.diploma.smartroads.model.exception.IncorrectInputDataException;
import com.kpi.diploma.smartroads.model.exception.IncorrectInviteKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(IncorrectInputDataException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectInputDataException(
            HttpServletRequest request, IncorrectInputDataException ex) {
        log.error("'handleIncorrectInputDataException' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getErrorMessage());
        return ResponseEntity.ok(ex.getErrorMessage());
    }

    @ExceptionHandler(IncorrectInviteKey.class)
    public ResponseEntity<ErrorMessage> handleIncorrectInviteUrl(
            HttpServletRequest request, IncorrectInviteKey ex) {
        log.error("'handleIncorrectInviteUrl' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getErrorMessage());
        return ResponseEntity.ok(ex.getErrorMessage());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorMessage> handleMessagingException(
            HttpServletRequest request, MessagingException ex) {
        log.error("'handleIncorrectInviteUrl' invoked for url'{}'", request.getRequestURL());
        log.error("'exceptionMessage={}'", ex.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), "MessagingException");
        return ResponseEntity.ok(errorMessage);
    }
}
