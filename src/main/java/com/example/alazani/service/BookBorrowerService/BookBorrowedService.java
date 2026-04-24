package com.example.alazani.service.BookBorrowerService;

import com.example.alazani.dto.CompressedBorrow;
import com.example.alazani.entity.Book;
import com.example.alazani.entity.BookBorrowed;
import com.example.alazani.entity.Borrower;
import com.example.alazani.repo.BlackListRepository;
import com.example.alazani.repo.BookBorrowedRepository;
import com.example.alazani.service.BookService;
import com.example.alazani.service.BorrowerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class BookBorrowedService {

    private final BookBorrowedRepository bookBorrowedRepo;
    private final BookService bookService;
    private final BorrowerService borrowerService;
    private final BlackListRepository blackListRepo;

    public BookBorrowedService(BookBorrowedRepository booksBorrowedRepo,
                               BookService bookService,
                               BorrowerService borrowerService,
                               BlackListRepository blackListRepo) {
        this.bookBorrowedRepo = booksBorrowedRepo;
        this.bookService = bookService;
        this.borrowerService = borrowerService;
        this.blackListRepo = blackListRepo;    // service DI causes circular dependency
    }

    public List<BookBorrowed> findAllBorrowings() {
        return bookBorrowedRepo.findAll();
    }

    public List<CompressedBorrow> findAllBorrowingsCompressed() {
        return bookBorrowedRepo.findAll().stream()
                .map(this::compressedBorrowMaker)
                .toList();
    }

    private CompressedBorrow compressedBorrowMaker(BookBorrowed borrowing) {
        String bookId = borrowing.getBook().getId();
        String bookName = borrowing.getBook().getName();
        String borrowerId = borrowing.getBorrower().getId();
        String borrowerName = borrowing.getBorrower().getName();

        return new CompressedBorrow(bookId, bookName, borrowerId, borrowerName,
                borrowing.getBorrowDate(), borrowing.getReturnDate());
    }

    public void saveToTable(String bookName, String borrowerId) {
        Book book = bookService.findAvailableByName(bookName);
        Borrower borrower = borrowerService.findById(borrowerId);
        if (blackListRepo.existsByBorrowerId(borrowerId)) {
            throw new RuntimeException("borrower in black list, borrowing rejected");
        }

        BookBorrowed bookBorrowed = new BookBorrowed(book, borrower);
        bookBorrowedRepo.save(bookBorrowed);          // written in BookBorrowed table
        bookService.setAvailabilityFalse(book.getId());       // book got borrowed
    }

    public void deleteFromTable(String bookId) {
        Book book = bookService.findById(bookId);
        if (book.isAvailable()) {
            throw new RuntimeException("book not borrowed");
        }

        bookBorrowedRepo.deleteByBook(book);
        bookService.setAvailabilityTrue(bookId);        // book got free
    }

    public void deleteAll() {
        bookBorrowedRepo.deleteAll();
        bookService.findAll().forEach(b -> bookService.setAvailabilityTrue(b.getId()));
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