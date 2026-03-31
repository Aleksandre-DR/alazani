package com.example.alazani.controller;

import com.example.alazani.entity.Borrower;
import com.example.alazani.service.BorrowerService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    private BorrowerService borrowerService;

    private BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping("/all")
    private ResponseEntity<List<Borrower>> findAllBorrowers(){
        List<Borrower> borrowers = borrowerService.findAllBorrowers();
        return ResponseEntity.ok(borrowers);
    }

    @PostMapping("/add")
    public void addToTable(@RequestBody @NotEmpty Borrower borrower) {
        borrowerService.addToTable(borrower);
    }

    @PostMapping("/delete")
    public void deleteFromTable(@RequestParam @NotEmpty String borrowerId) {
        borrowerService.deleteFromTable(borrowerId);
    }

    @GetMapping("/get")
    public ResponseEntity<Borrower> findById(@RequestParam @NotEmpty String borrowerId) {
        Borrower borrower = borrowerService.findById(borrowerId);
        return ResponseEntity.ok(borrower);
    }
}
