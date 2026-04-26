package com.example.alazani.controller;

import com.example.alazani.dto.BorrowRequest;
import com.example.alazani.dto.CompressedBorrow;
import com.example.alazani.entity.Book;
import com.example.alazani.entity.BookBorrowed;
import com.example.alazani.service.BookBorrowerService.BookBorrowedService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/borrowing", produces = "application/json")
public class BookBorrowedController {
    private final BookBorrowedService bookBorrowedService;

    public BookBorrowedController(BookBorrowedService bookBorrowedService) {
        this.bookBorrowedService = bookBorrowedService;
    }

    @GetMapping("/all")
    public List<BookBorrowed> findAllBorrowings() {
        return bookBorrowedService.findAllBorrowings();
    }

    @GetMapping("/all-compressed")
    public List<CompressedBorrow> findAllBorrowingsCompressed() {
        return bookBorrowedService.findAllBorrowingsCompressed();
    }

    @PostMapping("/add")
    public String saveToTable(@Valid @RequestBody BorrowRequest request) {
        String bookName = request.getBookName();
        String borrowerId = request.getBorrowerId();

        bookBorrowedService.saveToTable(bookName, borrowerId);
        return "saved successfully";
    }

    @PostMapping("/delete")
    public String deleteFromTable(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);

        bookBorrowedService.deleteFromTable(bookId);
        return "deleted successfully";
    }

    @PostMapping("/delete-all")
    public String deleteAllFromTable() {
        bookBorrowedService.deleteAll();
        return "deleted all table successfully";
    }

    @GetMapping("/books-borrowed-by")
    public List<Book> booksBorrowedBy(@RequestParam String borrowerId) {
        ControllerParameterChecker.checkBorrowerId(borrowerId);
        return bookBorrowedService.booksBorrowedBy(borrowerId);
    }

    @GetMapping("/borrow-date")
    public LocalDate bookBorrowDate(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);
        return bookBorrowedService.bookBorrowDate(bookId);
    }
}
