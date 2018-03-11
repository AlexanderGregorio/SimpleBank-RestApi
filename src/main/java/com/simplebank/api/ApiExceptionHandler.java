package com.simplebank.api;

import com.simplebank.exception.ElementAlreadyExistException;
import com.simplebank.model.ApiErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NoSuchElementException.class })
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        ApiErrorMessage errorMessage = new ApiErrorMessage(ex.getMessage(),
                request.getDescription(false).replace("uri=", ""));

        return handleExceptionInternal(ex, errorMessage,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { ElementAlreadyExistException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ApiErrorMessage errorMessage = new ApiErrorMessage(ex.getMessage(),
                request.getDescription(false).replace("uri=", ""));

        return handleExceptionInternal(ex, errorMessage,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
