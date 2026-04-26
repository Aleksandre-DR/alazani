package com.example.alazani.controller;

import com.example.alazani.entity.BlackList;
import com.example.alazani.service.BlackListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/blacklist", produces = "application/json")
public class BlackListController {
    private final BlackListService blackListService;

    public BlackListController(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    @PostMapping("delete")
    public String deleteFromTable(@RequestParam String bookId) {
        ControllerParameterChecker.checkBookId(bookId);

        blackListService.deleteFromTable(bookId);
        return "deleted successfully";
    }

    @GetMapping("all")
    public List<BlackList> findAll() {
        return blackListService.findAll();          // all blacklisters
    }

    @GetMapping("all-by")
    public List<BlackList> findAllBy(@RequestParam String borrowerId) {
        ControllerParameterChecker.checkBorrowerId(borrowerId);
        return blackListService.findAllByBorrowerId(borrowerId);
    }
}
