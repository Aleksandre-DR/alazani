package com.example.alazani.service.BookBorrowerService;

import com.example.alazani.entity.Book;
import com.example.alazani.entity.BookBorrowed;
import com.example.alazani.entity.Borrower;
import com.example.alazani.repo.BookBorrowedRepository;
import com.example.alazani.service.BlackListService;
import com.example.alazani.service.BookService;
import com.example.alazani.service.BorrowerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class BookBorrowedService {

    private BookBorrowedRepository bookBorrowedRepo;
    private BookService bookService;
    private BorrowerService borrowerService;
    private BlackListService blackListService;

    public BookBorrowedService(BookBorrowedRepository booksBorrowedRepo,
                               BookService bookService, BorrowerService borrowerService,
                               BlackListService blackListService) {
        this.bookBorrowedRepo = booksBorrowedRepo;
        this.bookService = bookService;
        this.borrowerService = borrowerService;
        this.blackListService = blackListService;
    }

    public List<BookBorrowed> findAllBorrowings() {
        return bookBorrowedRepo.findAll();
    }

    public void saveToTable(String bookName, String borrowerId) {
        Book book = bookService.findAvailableByName(bookName);
        Borrower borrower = borrowerService.findById(borrowerId);
        if (blackListService.existsByBorrowerId(borrowerId)) {
            throw new RuntimeException("borrower in black list, borrowing rejected");
        }

        BookBorrowed bookBorrowed = new BookBorrowed(book, borrower);
        bookBorrowedRepo.save(bookBorrowed);              // written in BookBorrowed table
        bookService.setAvailabilityOf(book.getId(), false);       // book got borrowed
    }

    public void deleteFromTable(String bookId) {
        Book book = bookService.findById(bookId);
        if (book.isAvailable()) {
            throw new RuntimeException("book not borrowed");
        }

        bookBorrowedRepo.deleteByBook(book);
        bookService.setAvailabilityOf(bookId, true);        // book got free
    }

    public void deleteAll() {
        bookBorrowedRepo.deleteAll();
        bookService.findAll().forEach(b -> bookService.setAvailabilityOf(b.getId(), true));
    }

    public List<Book> booksBorrowedBy(String borrowerId) {
        Borrower borrower = borrowerService.findById(borrowerId);
        if (!bookBorrowedRepo.existsByBorrower(borrower)) {
            throw new RuntimeException("no current borrowing for borrower");
        }

        List<BookBorrowed> borrowings = bookBorrowedRepo.findByBorrower(borrower);
        return borrowings.stream()
                .sorted(Comparator.comparing(BookBorrowed::getBorrowDate))
                .map(BookBorrowed::getBook)
                .toList();
    }

    public LocalDate bookBorrowDate(String bookId) {
        Book book = bookService.findById(bookId);
        BookBorrowed bb = findByBook(book);
        return bb.getBorrowDate();
    }

    private BookBorrowed findByBook(Book book) {
        return bookBorrowedRepo.findByBook(book)
                .orElseThrow(() -> new RuntimeException("book not borrowed"));
    }
}