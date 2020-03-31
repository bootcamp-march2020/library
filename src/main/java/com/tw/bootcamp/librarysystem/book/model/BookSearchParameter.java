package com.tw.bootcamp.librarysystem.book.model;

public class BookSearchParameter {
    private String author;
    private String bookName;
    private String category;

    public BookSearchParameter(String author, String bookName, String category) {
        this.author = author;
        this.bookName = bookName;
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public String getCategory() {
        return category;
    }
}
