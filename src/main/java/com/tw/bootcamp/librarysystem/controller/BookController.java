package com.tw.bootcamp.librarysystem.controller;

import com.tw.bootcamp.librarysystem.model.Book;
import com.tw.bootcamp.librarysystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("library-system/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    public List<Book> getBookList() {
        return bookService.getBooks();
    }

}
