package com.example.alazani.controller;

import com.example.alazani.entity.BlackList;
import com.example.alazani.service.BlackListService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blacklist")
public class BlackListController {
    private final BlackListService blackListService;

    public BlackListController(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    private static final String bookIdNotEmpty = "book id can not be empty";
    private static final String borrowerIdNotEmpty = "borrower id can not be empty";

    @PostMapping("delete")
    public ResponseEntity<String> deleteFromTable(@RequestParam @NotEmpty(message = bookIdNotEmpty) String bookId) {
        blackListService.deleteFromTable(bookId);
        return ResponseEntity.ok("deleted successfully");
    }

    @GetMapping("all")
    public ResponseEntity<List<BlackList>> findAll() {
        List<BlackList> blackListers = blackListService.findAll();
        return ResponseEntity.ok(blackListers);
    }

    @GetMapping("all-by")
    public ResponseEntity<List<BlackList>> findAllBy(@RequestParam @NotEmpty(message = borrowerIdNotEmpty) String borrowerId) {
        List<BlackList> blackListers = blackListService.findAllByBorrowerId(borrowerId);
        return ResponseEntity.ok(blackListers);
    }
}
