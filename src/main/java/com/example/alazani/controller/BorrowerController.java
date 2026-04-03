package com.example.alazani.controller;

import com.example.alazani.entity.Borrower;
import com.example.alazani.service.BorrowerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    private final BorrowerService borrowerService;

    private BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    private static final String idNotEmpty = "borrower id can not be empty";

    @GetMapping("/all")
    private ResponseEntity<List<Borrower>> findAllBorrowers() {
        List<Borrower> borrowers = borrowerService.findAllBorrowers();
        return ResponseEntity.ok(borrowers);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToTable(@RequestBody @Valid Borrower borrower) {
        borrowerService.addToTable(borrower);
        return ResponseEntity.ok("added successfully");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteFromTable(@RequestParam @NotBlank(message = idNotEmpty) String borrowerId) {
        borrowerService.deleteFromTable(borrowerId);
        return ResponseEntity.ok("deleted successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<Borrower> findById(@RequestParam @NotBlank(message = idNotEmpty) String borrowerId) {
        Borrower borrower = borrowerService.findById(borrowerId);
        return ResponseEntity.ok(borrower);
    }
}
