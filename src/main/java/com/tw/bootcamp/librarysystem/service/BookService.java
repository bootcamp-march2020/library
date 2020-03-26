package com.tw.bootcamp.librarysystem.service;

import com.tw.bootcamp.librarysystem.model.Book;
import com.tw.bootcamp.librarysystem.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
