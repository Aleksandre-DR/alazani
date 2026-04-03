package com.example.alazani.exception;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class ErrorResponse {
    private final String message;
    private final LocalDate timeStamp;

    public ErrorResponse(String message){
        this.message = message;
        this.timeStamp = LocalDate.now();
    }
}
