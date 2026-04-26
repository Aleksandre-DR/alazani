package com.example.alazani.controller;

import com.example.alazani.entity.Book;
import com.example.alazani.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book", produces = "application/json")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/all-by")
    public List<Book> findAllBy(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);
        return bookService.findAllBy(author);
    }

    @GetMapping("/distincts")
    public List<String> findDistincts() {
        List<String> bookNames = bookService.findDistincts();
        return bookNames;
    }

    @GetMapping("/distincts-by")
    public List<String> findDistinctsBy(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);
        return bookService.findDistinctsBy(author);
    }

    @GetMapping("/amount-of")
    public long numberOfBook(@RequestParam String bookName) {
        ControllerParameterChecker.checkBookName(bookName);
        return bookService.numberOfBook(bookName);
    }

    @GetMapping("/free-distincts")
    public List<String> findDistinctAvailables() {
        return bookService.findDistinctAvailables();
    }

    @GetMapping("/free-distincts-by")
    public List<String> findDistinctAvailablesBy(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);
        return bookService.findDistinctAvailablesBy(author);
    }

    @GetMapping("/exists")
    public boolean isBookIsStore(@RequestParam String bookName) {
        ControllerParameterChecker.checkBookName(bookName);
        return bookService.isBookInStore(bookName);
    }

    @GetMapping("/is-available")
    public boolean isBookAvailable(@RequestParam String bookName) {
        ControllerParameterChecker.checkBookName(bookName);
        return bookService.isBookAvailable(bookName);
    }

    @GetMapping("/exists-author")
    public boolean isAuthorIsStore(@RequestParam String author) {
        ControllerParameterChecker.checkAuthor(author);
        return bookService.isAuthorInStore(author);
    }

    @PostMapping("/fill-table")
    public String fillTable() {
        bookService.fillTable();
        return "filled successfully";
    }

    @PostMapping("delete")
    public String deleteFromTable(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);

        bookService.deleteFromTable(bookId);
        return "deleted successfully";
    }

    @PostMapping("delete-all")
    public String deleteAllFromTable() {
        bookService.deleteAllFromTable();
        return "deleted successfully";
    }
}
