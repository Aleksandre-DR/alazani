package com.example.alazani.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class CompressedBorrow {
    private String bookId;
    private String bookName;
    private String borrowerId;
    private String borrowerName;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
