package com.tw.bootcamp.librarysystem.book.service;

import com.tw.bootcamp.librarysystem.book.exception.BookNotFoundException;
import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
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
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void testGetBookDetail() throws Exception {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateString = "2001-07-04T12:08:56.235-0700";
        Date expectedReleaseDate = df1.parse(dateString);
        Book expectedBook = new Book();
        expectedBook.setId(1);
        expectedBook.setBookName("Android in Action, Second Edition");
        expectedBook.setReleaseDate(expectedReleaseDate);
        expectedBook.setAuthor("W. Frank Ableson");

        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
        given(bookRepository.findById(any()))
                .willReturn(Optional.of(expectedBook));

        Book book = bookService.getBookDetail(1);
        assertEquals(expectedBook.getAuthor(), book.getAuthor());
        assertEquals(expectedBook.getBookName(), book.getBookName());
        assertEquals(expectedBook.getReleaseDate(), book.getReleaseDate());
        assertEquals(expectedBook.getId(), book.getId());
    }

    @Test
    public void testGetBookDetailForInvalidId() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
        given(bookRepository.findById(any()))
                .willThrow(new BookNotFoundException());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.getBookDetail(1));
        assertTrue(exception.getMessage().equals("Book Id requested is not present."));
    }
}
