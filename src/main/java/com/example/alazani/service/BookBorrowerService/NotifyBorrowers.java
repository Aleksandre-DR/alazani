package com.example.alazani.service.BookBorrowerService;

import com.example.alazani.entity.BlackList;
import com.example.alazani.entity.BookBorrowed;
import com.example.alazani.service.BlackListService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NotifyBorrowers {

    private BookBorrowedService bookBorrowedService;
    private BlackListService blackListService;

    public NotifyBorrowers(BookBorrowedService bookBorrowedService,
                           BlackListService blackListService) {
        this.bookBorrowedService = bookBorrowedService;
        this.blackListService = blackListService;
    }

    @Scheduled(cron = "0 0 9 * * *")            // method is run everyday at 09:00 am
    public void checkBorrowersClosenessToDeadline() {
        notifyCloseToDeadliners();
        notifyOnDeadliners();
        notifyAfterDeadliners();
    }

    private void notifyCloseToDeadliners() {
        List<BookBorrowed> borrowings = borrowingsCloseToDeadline();
        String notifyMessage;

        for (var borrowing : borrowings) {
            notifyMessage = NotifyMessageMaker.closeToDeadlineMessage(borrowing);
            borrowing.getBorrower().notify(notifyMessage);
        }
    }

    private void notifyOnDeadliners() {
        List<BookBorrowed> borrowings = borrowingsOnDeadline();
        String notifyMessage;

        for (var borrowing : borrowings) {
            notifyMessage = NotifyMessageMaker.onDeadlineMessage(borrowing);
            borrowing.getBorrower().notify(notifyMessage);
        }
    }

    private void notifyAfterDeadliners() {
        List<BookBorrowed> borrowings = borrowingsAfterDeadline();
        String notifyMessage;

        for (var borrowing : borrowings) {
            notifyMessage = NotifyMessageMaker.afterDeadlineMessage(borrowing);

            borrowing.getBorrower().notify(notifyMessage);
            manageAccordingTables(borrowing);
        }
    }

    private void manageAccordingTables(BookBorrowed borrowing) {
        String bookName, bookId, borrowerId;

        bookName = borrowing.getBook().getName();
        bookId = borrowing.getBook().getId();
        borrowerId = borrowing.getBorrower().getId();

        bookBorrowedService.deleteFromTable(bookId);
        BlackList newRow = new BlackList(bookId, bookName, borrowerId);
        blackListService.addToTable(newRow);
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

    private List<BookBorrowed> borrowingsAfterDeadline() {
        LocalDate today = LocalDate.now();

        return bookBorrowedService.findAllBorrowings().stream()
                .filter(bb -> bb.getReturnDate().isAfter(today))
                .toList();          // deadlineDate < today
    }

}
