package com.example.alazani.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowRequest {

    @NotBlank(message = "book name can not be empty")
    private String bookName;

    @NotBlank(message = "borrower id can not be empty")
    private  String borrowerId;
}
