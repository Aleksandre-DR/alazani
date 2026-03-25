package com.example.alazani.exception;

public class AuthorNotInStoreException extends Exception{
    @Override
    public String getMessage() {
        return "not such author in store";
    }
}
