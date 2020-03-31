package com.tw.bootcamp.librarysystem.book.model;

public enum BookSearchKey {

    AUTHOR("author"),
    CATEGORY("category"),
    NAME("name");

    private String name;

    BookSearchKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
