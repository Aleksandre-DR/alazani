package com.example.alazani.controller;

import com.example.alazani.entity.Book;
import com.example.alazani.service.BookService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/all-by")
    public ResponseEntity<List<Book>> findAllBy(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);

        List<Book> books = bookService.findAllBy(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/distincts")
    public ResponseEntity<List<String>> findDistincts() {
        List<String> bookNames = bookService.findDistincts();
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/distincts-by")
    public ResponseEntity<List<String>> findDistinctsBy(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);

        List<String> bookNames = bookService.findDistinctsBy(author);
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/amount-of")
    public ResponseEntity<Long> numberOfBook(@RequestParam String bookName) {
        ControllerParameterChecker.checkBookName(bookName);

        long numberOfBook = bookService.numberOfBook(bookName);
        return ResponseEntity.ok(numberOfBook);
    }

    @GetMapping("/free-distincts")
    public ResponseEntity<List<String>> findDistinctAvailables() {
        List<String> bookNames = bookService.findDistinctAvailables();
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/free-distincts-by")
    public ResponseEntity<List<String>> findDistinctAvailablesBy(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);

        List<String> bookNames = bookService.findDistinctAvailablesBy(author);
        return ResponseEntity.ok(bookNames);
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> isBookIsStore(@RequestParam String bookName) {
        ControllerParameterChecker.checkBookName(bookName);

        boolean isBookInStore = bookService.isBookInStore(bookName);
        return ResponseEntity.ok(isBookInStore);
    }

    @GetMapping("/is-available")
    public ResponseEntity<Boolean> isBookAvailable(@RequestParam String bookName) {
        ControllerParameterChecker.checkBookName(bookName);

        boolean isAvailable = bookService.isBookAvailable(bookName);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/exists-author")
    public ResponseEntity<Boolean> isAuthorIsStore(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);

        boolean isAuthorInStore = bookService.isAuthorInStore(author);
        return ResponseEntity.ok(isAuthorInStore);
    }

    @PostMapping("/fill-table")
    public ResponseEntity<String> fillTable() {
        bookService.fillTable();
        return ResponseEntity.ok("filled successfully");
    }

    @PostMapping("delete")
    public ResponseEntity<String> deleteFromTable(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);

        bookService.deleteFromTable(bookId);
        return ResponseEntity.ok("deleted successfully");
    }

    @PostMapping("delete-all")
    public ResponseEntity<String> deleteAllFromTable() {
        bookService.deleteAllFromTable();
        return ResponseEntity.ok("deleted successfully");
    }
}
