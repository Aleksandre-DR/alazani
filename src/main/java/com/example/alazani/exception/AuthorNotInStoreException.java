package com.example.alazani.exception;

public class AuthorNotInStoreException extends RuntimeException{
    public AuthorNotInStoreException(){
        super("author not in store");
    }
}
