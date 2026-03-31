package com.example.alazani.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Book {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private boolean isAvailable;

    public Book(String id, String name, String author){
        this.id = id;
        this.name = name;
        this.author = author;
        this.isAvailable = true;
    }
}
