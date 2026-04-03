package com.example.alazani.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BlackList {
    @Id
    @Pattern(regexp = "\\d+", message = "book id must contain only digits")
    @Size(min = 7, max = 7, message = "book id must have exactly 7 digits")
    private String bookId;

    @NotBlank(message = "book name is required")
    private String bookName;

    @Pattern(regexp = "\\d+", message = "borrower id must contain only digits")
    @Size(min = 11, max = 11, message = "borrower id must have exactly 11 digits")
    private String borrowerId;
}
