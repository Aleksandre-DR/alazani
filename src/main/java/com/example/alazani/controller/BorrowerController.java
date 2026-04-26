package com.example.alazani.controller;

import com.example.alazani.entity.Borrower;
import com.example.alazani.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/borrower", produces = "application/json")
public class BorrowerController {
    private final BorrowerService borrowerService;

    private BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping("/all")
    private List<Borrower> findAllBorrowers() {
        return borrowerService.findAllBorrowers();
    }

    @PostMapping("/add")
    public String addToTable(@RequestBody @Valid Borrower borrower) {
        borrowerService.addToTable(borrower);
        return "added successfully";
    }

    @PostMapping("/delete")
    public String deleteFromTable(@RequestParam String borrowerId) {
        ControllerParameterChecker.checkBorrowerId(borrowerId);

        borrowerService.deleteFromTable(borrowerId);
        return "deleted successfully";
    }

    @GetMapping("/get")
    public Borrower findById(@RequestParam String borrowerId) {
        ControllerParameterChecker.checkBorrowerId(borrowerId);
        return borrowerService.findById(borrowerId);
    }
}
