package com.example.alazani.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Book {
    @Id
    @Pattern(regexp = "\\d+", message = "book id must contain only digits")
    @Size(min = 7, max = 7, message = "book id must have exactly 7 digits")
    private String id;

    @NotBlank(message = "book name is required")
    private String name;

    @NotBlank(message = "author name is required")
    private String author;

    @Column(nullable = false)
    private boolean isAvailable;

    public Book(String id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isAvailable = true;
    }
}
