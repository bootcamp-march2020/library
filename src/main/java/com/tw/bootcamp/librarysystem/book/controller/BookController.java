package com.tw.bootcamp.librarysystem.book.controller;

import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("library-system/books")
public class BookController {

    @Autowired
    BookService bookService;

    /**
     * Get all books Api
     *
     * @return List<Book> list of books
     */
    @GetMapping({"", "/"})
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }


    @GetMapping("/{id}")
    public Book getBookDetail(@PathVariable int id) {
        return bookService.getBookDetail(id);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(value = "author", required = false) String author,
                                  @RequestParam(value = "name", required = false) String bookName,
                                  @RequestParam(value = "category", required = false) String category) {

        return bookService.searchBooks(author, bookName, category);


    }

}
