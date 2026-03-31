package com.example.alazani.entity;

public interface Observer {
    void notifyCloseToDeadline(String bookName, int daysLeft);
    void notifyOnDeadline(String bookName);
}
