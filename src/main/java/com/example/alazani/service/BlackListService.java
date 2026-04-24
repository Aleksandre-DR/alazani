package com.example.alazani.service;

import com.example.alazani.entity.BlackList;
import com.example.alazani.repo.BlackListRepository;
import com.example.alazani.service.BookBorrowerService.BookBorrowedService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private final BlackListRepository blackListRepo;
    private final BookService bookService;
    private final BookBorrowedService bookBorrowedService;

    public BlackListService(BlackListRepository blackListRepo,
                            BookService bookService,
                            BookBorrowedService bookBorrowedService) {
        this.blackListRepo = blackListRepo;
        this.bookService = bookService;
        this.bookBorrowedService = bookBorrowedService;
    }

    public boolean existsByBorrowerId(String borrowerId) {
        return blackListRepo.existsByBorrowerId(borrowerId);
    }

    public void addToTable(BlackList blackList) {
        if (blackListRepo.existsById(blackList.getBookId())) return;
        blackListRepo.save(blackList);
    }

    @Transactional
    public void deleteFromTable(String bookId) {
        if (!blackListRepo.existsById(bookId)) {
            throw new RuntimeException("book not in black list");
        }
        blackListRepo.deleteById(bookId);
        bookBorrowedService.deleteFromTable(bookId);    // deleting from borrowing table
        bookService.setAvailabilityTrue(bookId);   // after returning, book got free
    }

    public List<BlackList> findAll() {
        return blackListRepo.findAll();
    }

    public List<BlackList> findAllByBorrowerId(String borrowerId) {
        if (!existsByBorrowerId(borrowerId)) {
            throw new RuntimeException("no such borrower in black list");
        }

        return blackListRepo.findAllByBorrowerId(borrowerId);
    }
}
