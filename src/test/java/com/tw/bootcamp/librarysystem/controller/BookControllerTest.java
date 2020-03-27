package com.tw.bootcamp.librarysystem.controller;

import com.tw.bootcamp.librarysystem.book.controller.BookController;
import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

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
        someBook.setName("android");
        given(bookService.getBooks())
                .willReturn(Arrays.asList(someBook));


        bookControllerMock.perform(MockMvcRequestBuilders.get("/library-system/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bookName").value("android"));
    }


    @Test
    public void testSearchBooksEndpoint() throws Exception {
        Book someBook = new Book();
        someBook.setId(1);
        someBook.setName("android");
        given(bookService.searchBooks(any(), any(), any()))
                .willReturn(Arrays.asList(someBook));


        bookControllerMock.perform(MockMvcRequestBuilders.get("/library-system/books/search?author=&name=android&category="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].bookName").value("android"));
    }

}
