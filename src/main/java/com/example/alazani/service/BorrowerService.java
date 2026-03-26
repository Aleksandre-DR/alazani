package com.example.alazani.service;

import com.example.alazani.entity.Borrower;
import com.example.alazani.exception.ResourceNotFoundException;
import com.example.alazani.repo.BorrowerRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {

    private BorrowerRepository borrowerRepo;

    private BorrowerService(BorrowerRepository borrowerRepo) {
        this.borrowerRepo = borrowerRepo;
    }

    public void addToDatabase(Borrower borrower) {
        if (borrowerRepo.existsById(borrower.getId())) return;
        borrowerRepo.save(borrower);
    }

    public void deleteFromDatabase(String borrowerId){
        if(!borrowerRepo.existsById(borrowerId)){
            throw new ResourceNotFoundException("no such borrower in database");
        }
        borrowerRepo.deleteById(borrowerId);
    }
}
