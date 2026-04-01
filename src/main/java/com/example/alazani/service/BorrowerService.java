package com.example.alazani.service;

import com.example.alazani.entity.Borrower;
import com.example.alazani.repo.BorrowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerService {

    private BorrowerRepository borrowerRepo;

    public BorrowerService(BorrowerRepository borrowerRepo) {
        this.borrowerRepo = borrowerRepo;
    }

    public List<Borrower> findAllBorrowers() {
        return borrowerRepo.findAll();
    }

    public void addToTable(Borrower borrower) {
        if (borrowerRepo.existsById(borrower.getId())) {
            throw new RuntimeException("borrower already exists");
        }
        borrowerRepo.save(borrower);
    }

    public void deleteFromTable(String borrowerId) {
        Borrower borrower = findById(borrowerId);
        borrowerRepo.delete(borrower);
    }

    public Borrower findById(String borrowerId) {
        return borrowerRepo.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("borrower not in store"));
    }
}
