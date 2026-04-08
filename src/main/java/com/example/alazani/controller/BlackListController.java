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

    @PostMapping("delete")
    public ResponseEntity<String> deleteFromTable(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);

        blackListService.deleteFromTable(bookId);
        return ResponseEntity.ok("deleted successfully");
    }

    @GetMapping("all")
    public ResponseEntity<List<BlackList>> findAll() {
        List<BlackList> blackListers = blackListService.findAll();
        return ResponseEntity.ok(blackListers);
    }

    @GetMapping("all-by")
    public ResponseEntity<List<BlackList>> findAllBy(@RequestParam String borrowerId) {
        ControllerParameterChecker.checkBorrowerId(borrowerId);

        List<BlackList> blackListers = blackListService.findAllByBorrowerId(borrowerId);
        return ResponseEntity.ok(blackListers);
    }
}
