package com.tw.bootcamp.librarysystem.book.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tw.bootcamp.librarysystem.book.model.*;
import com.tw.bootcamp.librarysystem.book.model.dto.BookDTO;
import com.tw.bootcamp.librarysystem.book.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    ModelMapper modelMapper;
    
    @JsonView(Views.List.class)
    @GetMapping({"", "/"})
    public List<BookDTO> getAllBooks() {
        return createBookDTOList(bookService.getBooks());
    }


    @GetMapping("/{id}")
    public BookDTO getBookDetail(@PathVariable int id) {
        return createBookDTO(bookService.getBookDetail(id));
    }

    @GetMapping("/search")
    @JsonView(Views.List.class)
    public List<BookDTO> searchBooks(@RequestParam(value = "author", required = false) String author,
                                  @RequestParam(value = "name", required = false) String bookName,
                                  @RequestParam(value = "category", required = false) String category) {
        List<SearchCriteria> criteria = new ArrayList<>();
        if(!StringUtils.isEmpty(author)) {
            criteria.add(new SearchCriteria(BookSearchKey.AUTHOR.getName(), author));
        }
        if(!StringUtils.isEmpty(category)) {
            criteria.add(new SearchCriteria(BookSearchKey.CATEGORY.getName(), category));
        }
        if(!StringUtils.isEmpty(bookName)) {
            criteria.add(new SearchCriteria(BookSearchKey.NAME.getName(), bookName));
        }
        return createBookDTOList(bookService.searchBooks(criteria));
    }


    private BookDTO createBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    private List<BookDTO> createBookDTOList(List<Book> books) {
        return books.stream().map(this::createBookDTO).collect(Collectors.toList());
    }

}
