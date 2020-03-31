package com.tw.bootcamp.librarysystem.book.service;

import com.tw.bootcamp.librarysystem.book.exception.BookNotFoundException;
import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.model.BookSearchParameter;
import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
import com.tw.bootcamp.librarysystem.book.repository.BookSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    private BookRepository bookRepository;

    private BookService bookService;

    private Book androidBook;

    @BeforeEach
    public void setup(){
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);

        androidBook = new Book();
        androidBook.setId(1);
        androidBook.setName("android");

        Book anotherBook = new Book();
        anotherBook.setId(2);
        anotherBook.setName("Web");

        given(bookRepository.findAll())
                .willReturn(Arrays.asList(androidBook, anotherBook));
    }

    @Test
    public void whenGetBookListShouldReturnBookList() {
        List<Book> books = bookService.getBooks();
        assertFalse (books.isEmpty());
        assertEquals (books.get(0).getName(),"android");
    }

    @Test
    public void whenValidBookIdShouldReturnBookDetail() throws Exception {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateString = "2001-07-04T12:08:56.235-0700";
        Date expectedReleaseDate = df1.parse(dateString);
        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setName("Android in Action, Second Edition");
        expectedBook.setReleaseDate(expectedReleaseDate);
        expectedBook.setAuthor("W. Frank Ableson");

        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
        given(bookRepository.findById(any()))
                .willReturn(Optional.of(expectedBook));

        Book book = bookService.getBookDetail(1);
        assertEquals(expectedBook.getAuthor(), book.getAuthor());
        assertEquals(expectedBook.getName(), book.getName());
        assertEquals(expectedBook.getReleaseDate(), book.getReleaseDate());
        assertEquals(expectedBook.getId(), book.getId());
    }

    @Test
    public void whenInvalidBookIdShouldThrowBookNotFoundException() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
        given(bookRepository.findById(any()))
                .willThrow(new BookNotFoundException());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.getBookDetail(1));
        assertEquals(exception.getMessage(),"Book Id requested is not present.");
    }

    @Test
    public void testSearchBooks() {
        given(bookRepository.findAll((BookSpecification) any()))
                .willReturn(Arrays.asList(androidBook));
        List<Book> books = bookService.searchBooks(new BookSearchParameter("mockAuthor", "mockName", "mockCategory"));
        assertEquals(1, books.size());
    }

    @Test
    public void testSearchBooksWithNullParams() {
        given(bookRepository.findAll((BookSpecification) any()))
                .willReturn(Arrays.asList(androidBook));
        List<Book> books = bookService.searchBooks(new BookSearchParameter(null, null, null));
        assertEquals(2, books.size());
    }
}
