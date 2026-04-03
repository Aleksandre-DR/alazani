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

    private final BookBorrowedService bookBorrowedService;
    private final BlackListService blackListService;

    public NotifyBorrowers(BookBorrowedService bookBorrowedService,
                           BlackListService blackListService) {
        this.bookBorrowedService = bookBorrowedService;
        this.blackListService = blackListService;
    }

    @Scheduled(cron = "0 0 9 * * *")            // method is run every day at 09:00
    public void checkBorrowersClosenessToDeadline() {
        notifyCloseToDeadliners();
        notifyOnDeadliners();
        notifyDeadlineJustCrossers();
        notifyDeadlineLongAgoCrossers();
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

    private void notifyDeadlineJustCrossers() {
        List<BookBorrowed> borrowings = borrowingsJustCrossedDeadline();
        String notifyMessage;

        for (var borrowing : borrowings) {
            notifyMessage = NotifyMessageMaker.DeadlineJustCrossMessage(borrowing);

            borrowing.getBorrower().notify(notifyMessage);
            saveToBlackListTable(borrowing);
        }
    }

    private void notifyDeadlineLongAgoCrossers(){
        List<BookBorrowed> borrowings = borrowingsLongAgoCrossedDeadline();
        String notifyMessage;

        for(var borrowing : borrowings){
            notifyMessage = NotifyMessageMaker.DeadlineLongAgoCrossMessage(borrowing);
            borrowing.getBorrower().notify(notifyMessage);
        }
    }

    private void saveToBlackListTable(BookBorrowed borrowing) {
        String bookName, bookId, borrowerId;

        bookName = borrowing.getBook().getName();
        bookId = borrowing.getBook().getId();
        borrowerId = borrowing.getBorrower().getId();

        BlackList newRow = new BlackList(bookId, bookName, borrowerId);
        blackListService.addToTable(newRow);
    }

    private List<BookBorrowed> borrowingsCloseToDeadline() {
        LocalDate today = LocalDate.now();
        LocalDate sixDaysAfterToday = today.plusDays(6);

        return bookBorrowedService.findAllBorrowings().stream()
                .filter(bb -> bb.getReturnDate().isBefore(sixDaysAfterToday))
                .filter(bb -> bb.getReturnDate().isAfter(today))
                .toList();          // deadlineDate in 1..4 days
    }

    private List<BookBorrowed> borrowingsOnDeadline() {
        LocalDate today = LocalDate.now();

        return bookBorrowedService.findAllBorrowings().stream()
                .filter(bb -> bb.getReturnDate().isEqual(today))
                .toList();          // deadlineDate = today
    }

    private List<BookBorrowed> borrowingsJustCrossedDeadline() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        return bookBorrowedService.findAllBorrowings().stream()
                .filter(bb -> bb.getReturnDate().isEqual(yesterday))
                .toList();          // deadlineDate = yesterday
    }

    private List<BookBorrowed> borrowingsLongAgoCrossedDeadline(){
        LocalDate yesterday = LocalDate.now().minusDays(1);

        return bookBorrowedService.findAllBorrowings().stream()
                .filter(bb -> bb.getReturnDate().isBefore(yesterday))
                .toList();          // deadlineDate < yesterday
    }

}
