package com.example.alazani.service.BookBorrowerService;

import com.example.alazani.entity.BookBorrowed;

public class NotifyMessageMaker {

    private NotifyMessageMaker(){
    }


    static String closeToDeadlineMessage(BookBorrowed borrowing) {
        String borrowerName = borrowing.getBorrower().getName();
        String bookName = borrowing.getBook().getName();
        int daysLeft = borrowing.getBorrowDate().until(borrowing.getReturnDate()).getDays();

        return "hello " + borrowerName + ", we want to remained you that the book you " +
                "borrowed \"" + bookName + "\" should be returned in " + daysLeft + " days";
    }

    static String onDeadlineMessage(BookBorrowed borrowing) {
        String borrowerName = borrowing.getBorrower().getName();
        String bookName = borrowing.getBook().getName();

        return "hello " + borrowerName + ", we want to inform you that the book " +
                "you borrowed \"" + bookName + "\" should be returned today. otherwise, " +
                "your future borrowing attempts will be restricted.";
    }

    static String afterDeadlineMessage(BookBorrowed borrowing) {
        String borrowerName = borrowing.getBorrower().getName();
        String bookName = borrowing.getBook().getName();

        return "hello " + borrowerName + ", we want to inform you that you crossed the " +
                "return deadline for the book \"" + bookName + "\". your future borrowing " +
                "attempts will be restricted until you return all the borrowed books.";
    }
}
