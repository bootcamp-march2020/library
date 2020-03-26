package com.tw.bootcamp.librarysystem.book.controller;

import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("library-system/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping({"","/"})
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

}
