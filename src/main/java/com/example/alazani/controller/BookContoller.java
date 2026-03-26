package com.example.alazani.controller;

import com.example.alazani.entity.Book;
import com.example.alazani.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookContoller {

    private BookService bookService;

    public BookContoller(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/book-number")
    public ResponseEntity<Long> numberOfBook(@RequestParam String bookName){
        long numberOfBook = bookService.numberOfBook(bookName);
        return ResponseEntity.ok(numberOfBook);
    }

    @GetMapping("/books-by-author")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam String author){
        List<Book> books = bookService.booksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/fill-database")
    public void fillDatabase(){
        bookService.fillDatabase();
    }
}
