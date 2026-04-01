package com.example.alazani.exception;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class ErrorResponse {
    private String message;
    private LocalDate timeStamp;

    public ErrorResponse(String message){
        this.message = message;
        this.timeStamp = LocalDate.now();
    }
}
