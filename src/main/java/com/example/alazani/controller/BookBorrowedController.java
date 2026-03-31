package com.example.alazani.controller;

import com.example.alazani.entity.Book;
import com.example.alazani.entity.BookBorrowed;
import com.example.alazani.service.BookBorrowerService.BookBorrowedService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BookBorrowedController {
    private BookBorrowedService bookBorrowedService;

    private BookBorrowedController(BookBorrowedService bookBorrowedService){
        this.bookBorrowedService = bookBorrowedService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookBorrowed>> findAllBorrowings(){
        List<BookBorrowed> borrowings = bookBorrowedService.findAllBorrowings();
        return ResponseEntity.ok(borrowings);
    }

    @PostMapping("/add")
    private void saveToTable(@RequestParam @NotEmpty String bookName,
                             @RequestParam @NotEmpty String borrowerId){
        bookBorrowedService.saveToTable(bookName, borrowerId);
    }

    @PostMapping("/delete")
    public void deleteFromTable(@RequestParam @NotEmpty String bookId){
        bookBorrowedService.deleteFromTable(bookId);
    }

    @PostMapping("/delete-all")
    public void deleteAllFromTable(){
        bookBorrowedService.deleteAll();
    }

    @GetMapping("/books-borrowed-by")
    public ResponseEntity<List<Book>> booksBorrowedBy(@RequestParam @NotEmpty String borrowerId){
        List<Book> books = bookBorrowedService.booksBorrowedBy(borrowerId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/borrow-date")
    public ResponseEntity<LocalDate> bookBorrowDate(@RequestParam @NotEmpty String bookId){
        LocalDate borrowDate = bookBorrowedService.bookBorrowDate(bookId);
        return ResponseEntity.ok(borrowDate);
    }
}
