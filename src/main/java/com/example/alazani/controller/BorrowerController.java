package com.example.alazani.controller;

import com.example.alazani.entity.Borrower;
import com.example.alazani.service.BorrowerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    private BorrowerService borrowerService;

    private BorrowerController(BorrowerService borrowerService){
        this.borrowerService = borrowerService;
    }

    @PostMapping("/add")
    public void addToDatabase(@RequestBody Borrower borrower){
        borrowerService.addToDatabase(borrower);
    }

    @PostMapping("/delete")
    public void deleteFromDatabase(@RequestParam String borrowerId){
        borrowerService.deleteFromDatabase(borrowerId);
    }
}
