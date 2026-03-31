package com.example.alazani.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Borrower implements Observer {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    public void notifyCloseToDeadline(String bookName, int daysLeft) {
        String notifyMessage = "hello " + name + ", we want to remaind you that the book" +
                " you borrowed \"" + bookName + "\" should be return in " + daysLeft + " days";
        System.out.println(notifyMessage);     // sending message to borrowers phone number
    }

    public void notifyOnDeadline(String bookName) {
        String notifyMessage = "hello " + name + ", we want to inform you that the book " +
                "you borrowed \"" + bookName + "\" should be returned today. otherwise " +
                "your future borrowing attempts will be restricted.";
        System.out.println(notifyMessage);    // sending message to borrowers phone number
    }
}
