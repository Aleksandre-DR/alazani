package com.example.alazani.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Borrower implements Observer {
    @Id
    @Pattern(regexp = "\\d+", message = "borrower id must contain only digits")
    @Size(min = 11, max = 11, message = "borrower id must contain exactly 11 digits")
    private String id;

    @NotBlank(message = "borrower name is required")
    private String name;

    @NotBlank(message = "borrower surname is required")
    private String surname;

    @Column(unique = true)
    @Pattern(regexp = "\\d+", message = "phone number must contain only digits")
    @Size(min = 9, max = 9, message = "phone number must contain exactly 9 digits")
    private String phoneNumber;

    public void notify(String notifyMessage) {
        System.out.println(notifyMessage);     // sending message to borrowers phone number
    }
}
