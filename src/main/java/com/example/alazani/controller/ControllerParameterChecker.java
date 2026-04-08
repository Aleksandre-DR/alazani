package com.example.alazani.controller;

import com.example.alazani.exception.ParameterAbsentException;

// ensures URL parameters are not null, empty or blank strings
public class ControllerParameterChecker {
    static void checkBookId(String bookId) {
        check(bookId, "book id");
    }

    static void checkBookName(String bookName) {
        check(bookName, "book name");
    }

    static void checkBorrowerId(String borrowerId) {
        check(borrowerId, "borrower id");
    }

    static void checkAuthor(String author) {
        check(author, "author");
    }

    static void check(String parameter, String parameterName) {
        if (parameter == null) {
            throw new ParameterAbsentException(parameterName + " can not be null");
        }

        if (parameter.isEmpty()) {
            throw new ParameterAbsentException(parameterName + " can not be empty");
        }

        if (parameter.trim().isEmpty()) {
            throw new ParameterAbsentException(parameterName + " can not be blank");
        }
    }
}
