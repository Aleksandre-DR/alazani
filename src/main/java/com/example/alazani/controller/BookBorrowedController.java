package com.example.alazani.controller;

import com.example.alazani.dto.BorrowRequest;
import com.example.alazani.dto.CompressedBorrow;
import com.example.alazani.entity.Book;
import com.example.alazani.entity.BookBorrowed;
import com.example.alazani.service.BookBorrowerService.BookBorrowedService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/borrowing")
public class BookBorrowedController {
    private final BookBorrowedService bookBorrowedService;

    public BookBorrowedController(BookBorrowedService bookBorrowedService) {
        this.bookBorrowedService = bookBorrowedService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookBorrowed>> findAllBorrowings() {
        List<BookBorrowed> borrowings = bookBorrowedService.findAllBorrowings();
        return ResponseEntity.ok(borrowings);
    }

    @GetMapping("/all-compressed")
    public ResponseEntity<List<CompressedBorrow>> findAllBorrowingsCompressed() {
        List<CompressedBorrow> borrowings = bookBorrowedService.findAllBorrowingsCompressed();
        return ResponseEntity.ok(borrowings);
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveToTable(@Valid @RequestBody BorrowRequest request) {
        bookBorrowedService.saveToTable(request.getBookName(), request.getBorrowerId());
        return ResponseEntity.ok("saved successfully");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteFromTable(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);

        bookBorrowedService.deleteFromTable(bookId);
        return ResponseEntity.ok("deleted successfully");
    }

    @PostMapping("/delete-all")
    public ResponseEntity<String> deleteAllFromTable() {
        bookBorrowedService.deleteAll();
        return ResponseEntity.ok("deleted all table successfully");
    }

    @GetMapping("/books-borrowed-by")
    public ResponseEntity<List<Book>> booksBorrowedBy(@RequestParam String borrowerId) {
        ControllerParameterChecker.checkBorrowerId(borrowerId);

        List<Book> books = bookBorrowedService.booksBorrowedBy(borrowerId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/borrow-date")
    public ResponseEntity<LocalDate> bookBorrowDate(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);

        LocalDate borrowDate = bookBorrowedService.bookBorrowDate(bookId);
        return ResponseEntity.ok(borrowDate);
    }
}
