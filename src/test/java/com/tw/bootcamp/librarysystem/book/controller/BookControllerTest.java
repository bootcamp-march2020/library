package com.tw.bootcamp.librarysystem.book.controller;

import com.tw.bootcamp.librarysystem.book.exception.BookNotFoundException;
import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.service.BookService;
import com.tw.bootcamp.librarysystem.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc bookControllerMock;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetBooksEndpoint() throws Exception {
        Book someBook = new Book();
        someBook.setId(1);
        someBook.setBookName("android");
        given(bookService.getBooks())
                .willReturn(Arrays.asList(someBook));


        bookControllerMock.perform(MockMvcRequestBuilders.get("/library-system/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bookName").value("android"));
    }


    @Test
    public void testBookDetailEndpoint() throws Exception {
        bookControllerMock.perform(MockMvcRequestBuilders.get("/library-system/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testBookDetaisForGivenId() throws Exception {

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateString = "2001-07-04T12:08:56.235-0700";
        Date expectedReleaseDate = df1.parse(dateString);
        String expectedDateString = DateUtil.toISO8601UTC(expectedReleaseDate);

        Book someBook = new Book();
        someBook.setId(1);
        someBook.setBookName("Android in Action, Second Edition");
        someBook.setReleaseDate(expectedReleaseDate);
        someBook.setAuthor("W. Frank Ableson");

        given(bookService.getBookDetail(any()))
                .willReturn(someBook);

        bookControllerMock.perform(MockMvcRequestBuilders.get("/library-system/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("bookName", is("Android in Action, Second Edition")))
                .andExpect(jsonPath("author", is("W. Frank Ableson")))
                .andExpect(jsonPath("releaseDate", is(expectedDateString)));

    }

    @Test
    public void testBookDetailForInCorrectId() throws Exception {
        given(bookService.getBookDetail(1222))
                .willThrow(new BookNotFoundException());

        bookControllerMock.perform(MockMvcRequestBuilders.get("/library-system/books/1222"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Book Id requested is not present.")));

    }


}