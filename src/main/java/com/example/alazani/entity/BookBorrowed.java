package com.example.alazani.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class BookBorrowed {
    private static final int borrowDuration = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // if we want to map this entity to other entity, we store other entity as a field.
    // in table, this field will be stored as entity's id field.

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    public BookBorrowed(Book book, Borrower borrower){
        this.book = book;
        this.borrower = borrower;
        this.borrowDate = LocalDate.now();
        this.returnDate = borrowDate.plusDays(borrowDuration);
    }
}
