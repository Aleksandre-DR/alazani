package com.example.alazani.service;

import com.example.alazani.entity.BlackList;
import com.example.alazani.repo.BlackListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private final BlackListRepository blackListRepo;

    public BlackListService(BlackListRepository blackListRepo) {
        this.blackListRepo = blackListRepo;
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
