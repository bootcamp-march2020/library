package com.tw.bootcamp.librarysystem.book.service;

import com.tw.bootcamp.librarysystem.book.exception.BookNotFoundException;
import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
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

    public Book getBookDetail(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException());
    }
}
