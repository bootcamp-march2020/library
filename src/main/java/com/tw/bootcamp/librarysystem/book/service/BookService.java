package com.tw.bootcamp.librarysystem.book.service;

import com.tw.bootcamp.librarysystem.book.exception.BookNotFoundException;
import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.model.SearchCriteria;
import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
import com.tw.bootcamp.librarysystem.book.repository.BookSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Book> searchBooks(String author, String bookName, String category) {
        List<BookSpecification> specifications = new ArrayList<>();
        if (author != null) {
            specifications.add(new BookSpecification(new SearchCriteria("author", author)));
        }
        if (bookName != null) {
            specifications.add(new BookSpecification(new SearchCriteria("bookname", bookName)));
        }
        if (category != null) {
            specifications.add(new BookSpecification(new SearchCriteria("category", author)));
        }
        if (!specifications.isEmpty()) {
            Specification result = specifications.get(0);
            for (int i = 1; i < specifications.size(); i++) {
                result = Specification.where(result).and(specifications.get(i));
            }
            return bookRepository.findAll(result);
        }
        return bookRepository.findAll();
    }

}
