package com.tw.bootcamp.librarysystem.book.service;

import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {


    private BookRepository bookRepository;

    private BookService bookService;

    @Test
    public void testGetBooks() {
        Book someBook = new Book();
        someBook.setId(1);
        someBook.setBookName("android");
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
        given(bookRepository.findAll())
                .willReturn(Arrays.asList(someBook));

        List<Book> books = bookService.getBooks();
        assert (!books.isEmpty());
        assert (books.get(0).getBookName().equals("android"));
    }
}