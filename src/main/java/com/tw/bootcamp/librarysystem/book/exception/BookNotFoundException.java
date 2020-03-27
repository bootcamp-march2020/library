package com.tw.bootcamp.librarysystem.book.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Book Id requested is not present.");
    }
}
