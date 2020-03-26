package com.tw.bootcamp.librarysystem.controller;

import com.tw.bootcamp.librarysystem.model.Book;
import com.tw.bootcamp.librarysystem.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

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


         bookControllerMock.perform(MockMvcRequestBuilders.get("/library-system/book"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$[0].id", is(1)))
                 .andExpect(jsonPath("$[0].bookName").value("android"));
    }
}
