package com.example.alazani.exception;

public class BookNotInStoreException extends Exception{
    @Override
    public String getMessage() {
        return "no such book in store";
    }
}
