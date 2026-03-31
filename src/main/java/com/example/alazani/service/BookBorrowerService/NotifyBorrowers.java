package com.example.alazani.service.BookBorrowerService;

import com.example.alazani.entity.BookBorrowed;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NotifyBorrowers {

    private BookBorrowedService bookBorrowedService;

    public NotifyBorrowers(BookBorrowedService bookBorrowedService) {
        this.bookBorrowedService = bookBorrowedService;
    }

    @Scheduled(cron = "0 0 9 * * *")            // method is run everyday at 09:00 am
    public void checkBorrowersClosenessToDeadline() {
        notifyCloseToDeadliners();
        notifyOnDeadliners();
    }

    private void notifyCloseToDeadliners() {
        List<BookBorrowed> borrowings = borrowingsCloseToDeadline();
        String bookName;
        int daysLeft;

        for (var borrowing : borrowings) {
            bookName = borrowing.getBook().getName();
            daysLeft = borrowing.getBorrowDate().until(borrowing.getReturnDate()).getDays();
            borrowing.getBorrower().notifyCloseToDeadline(bookName, daysLeft);
        }
    }

    private void notifyOnDeadliners() {
        List<BookBorrowed> borrowings = borrowingsOnDeadline();
        String bookName;

        for (var borrowing : borrowings) {
            bookName = borrowing.getBook().getName();
            borrowing.getBorrower().notifyOnDeadline(bookName);
        }
    }

    private List<BookBorrowed> borrowingsCloseToDeadline() {
        LocalDate sixDaysAfterToday = LocalDate.now().plusDays(6);

        return bookBorrowedService.findAllBorrowings().stream()
                .filter(bb -> bb.getReturnDate().isBefore(sixDaysAfterToday))
                .toList();          // deadlineDate - today <= 5 days
    }

    private List<BookBorrowed> borrowingsOnDeadline() {
        LocalDate today = LocalDate.now();

        return bookBorrowedService.findAllBorrowings().stream()
                .filter(bb -> bb.getReturnDate().isEqual(today))
                .toList();          // deadlineDate = today
    }
}
