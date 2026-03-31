package com.example.alazani.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotInStoreException.class)
    public ResponseEntity<ErrorResponse> bookNotInStore(BookNotInStoreException ex){
        int status = HttpStatus.NO_CONTENT.value();
        ErrorResponse error = new ErrorResponse(ex.getMessage(), status);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex) {
        int status = HttpStatus.NO_CONTENT.value();
        ErrorResponse error = new ErrorResponse(ex.getMessage(), status);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> resourceAlreadyExists(ResourceAlreadyExistsException ex){
        int status = HttpStatus.FOUND.value();
        ErrorResponse error = new ErrorResponse(ex.getMessage(), status);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> validationException(HandlerMethodValidationException ex){
        String message = "input parameter must not be empty";
        int status = HttpStatus.BAD_REQUEST.value();
        ErrorResponse error = new ErrorResponse(message, status);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> undifinedRuntimeException(RuntimeException ex) {
        String message = "undifined exception: " + ex.getMessage();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorResponse error = new ErrorResponse(message, status);
        return ResponseEntity.ok(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> undifinedException(Exception ex) {
        String message = "undifined exception: " + ex.getMessage();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorResponse error = new ErrorResponse(message, status);
        return ResponseEntity.ok(error);
    }

}
