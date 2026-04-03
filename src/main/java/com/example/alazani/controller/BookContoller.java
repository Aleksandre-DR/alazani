package com.example.alazani.controller;

import com.example.alazani.entity.Book;
import com.example.alazani.service.BookService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookContoller {

    private BookService bookService;

    public BookContoller(BookService bookService) {
        this.bookService = bookService;
    }

    private static final String bookNotEmpty = "book name can not be empty";
    private static final String authorNotEmpty = "author name can not be empty";

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/all-by")
    public ResponseEntity<List<Book>> findAllBy(@RequestParam @NotBlank(message = authorNotEmpty) String author) {
        List<Book> books = bookService.findAllBy(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/distincts")
    public ResponseEntity<List<String>> findDistincts() {
        List<String> bookNames = bookService.findDistincts();
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/distincts-by")
    public ResponseEntity<List<String>> findDistinctsBy(@RequestParam @NotBlank(message = authorNotEmpty) String author) {
        List<String> bookNames = bookService.findDistinctsBy(author);
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/amount-of")
    public ResponseEntity<Long> numberOfBook(@RequestParam @NotBlank(message = bookNotEmpty) String bookName) {
        long numberOfBook = bookService.numberOfBook(bookName);
        return ResponseEntity.ok(numberOfBook);
    }

    @GetMapping("/free-distincts")
    public ResponseEntity<List<String>> findDistinctAvailables() {
        List<String> bookNames = bookService.findDistinctAvailables();
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/free-distincts-by")
    public ResponseEntity<List<String>> findDistinctAvailablesBy(@RequestParam @NotBlank(message = authorNotEmpty) String author) {
        List<String> bookNames = bookService.findDistinctAvailablesBy(author);
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> isBookIsStore(@RequestParam @NotBlank(message = bookNotEmpty) String bookName) {
        boolean isBookInStore = bookService.isBookInStore(bookName);
        return ResponseEntity.ok(isBookInStore);
    }

    @GetMapping("/is-available")
    public ResponseEntity<Boolean> isBookAvailable(@RequestParam @NotBlank(message = bookNotEmpty) String bookName) {
        boolean isAvailable = bookService.isBookAvailable(bookName);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/exists-author")
    public ResponseEntity<Boolean> isAuthorIsStore(@RequestParam @NotBlank(message = authorNotEmpty) String author) {
        boolean isAuthorInStore = bookService.isAuthorInStore(author);
        return ResponseEntity.ok(isAuthorInStore);
    }

    @PostMapping("/fill-table")
    public ResponseEntity<String> fillTable() {
        bookService.fillTable();
        return ResponseEntity.ok("filled successfully");
    }

    @PostMapping("delete-table")
    public ResponseEntity<String> deleteTable() {
        bookService.deleteTable();
        return ResponseEntity.ok("deleted successfully");
    }
}
