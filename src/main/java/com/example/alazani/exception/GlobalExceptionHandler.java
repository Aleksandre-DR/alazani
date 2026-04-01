package com.example.alazani.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotInStoreException.class)
    public ResponseEntity<ErrorResponse> bookNotInStore(BookNotInStoreException ex) {
        int status = HttpStatus.NOT_FOUND.value();

        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(AuthorNotInStoreException.class)
    public ResponseEntity<ErrorResponse> authorNotInStore(AuthorNotInStoreException ex) {
        int status = HttpStatus.NOT_FOUND.value();

        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)      // @RequestBody error
    public ResponseEntity<ErrorResponse> methodArgNotValid(MethodArgumentNotValidException ex) {
        String message = getValidationExceptionMessage(ex);

        ErrorResponse error = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)     // @RequestParam error
    public ResponseEntity<ErrorResponse> handlerMethodNotValid(HandlerMethodValidationException ex) {
        String message = getValidationExceptionMessage(ex);

        ErrorResponse error = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> undefinedException(Exception ex) {
        String message = "undefined exception: " + ex.getMessage();

        ErrorResponse error = new ErrorResponse(message);
        return ResponseEntity.internalServerError().body(error);
    }

    private String getValidationExceptionMessage(HandlerMethodValidationException ex) {
        return ex.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("undifined validation error");
    }

    private String getValidationExceptionMessage(MethodArgumentNotValidException ex) {
        return ex.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("undifined validation error");
    }
}
