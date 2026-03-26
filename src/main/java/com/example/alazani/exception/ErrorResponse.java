package com.example.alazani.exception;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDate timeStamp;

    public ErrorResponse(String message, int status){
        this.message = message;
        this.status = status;
        this.timeStamp = LocalDate.now();
    }
}
