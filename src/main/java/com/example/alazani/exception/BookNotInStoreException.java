package com.example.alazani.exception;

public class BookNotInStoreException extends RuntimeException{
    public BookNotInStoreException(){
        super("book not in store");
    }
}
