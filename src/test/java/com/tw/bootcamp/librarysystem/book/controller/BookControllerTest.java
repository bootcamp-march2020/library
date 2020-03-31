package com.tw.bootcamp.librarysystem.book.controller;

import com.tw.bootcamp.librarysystem.book.exception.BookNotFoundException;
import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.model.BookSearchParameter;
import com.tw.bootcamp.librarysystem.book.model.PriceCategory;
import com.tw.bootcamp.librarysystem.book.model.PriceInfo;
import com.tw.bootcamp.librarysystem.book.service.BookService;
import com.tw.bootcamp.librarysystem.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private MockMvc bookControllerMock;

    @MockBean
    private BookService bookService;

    @Autowired
    private WebApplicationContext context;

    public static final String BASE_URL="/books";


    @BeforeEach
    public void setUp(){
        bookControllerMock = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "testuser")
    public void shouldReturnBookList() throws Exception {
        Book someBook = new Book();
        someBook.setId(1);
        someBook.setName("android");
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setPricingCategory(PriceCategory.DEFAULT.name());
        someBook.setPriceInfo(priceInfo);
        given(bookService.getBooks())
                .willReturn(Arrays.asList(someBook));


        bookControllerMock.perform(MockMvcRequestBuilders.get(BASE_URL))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name").value("android"))
                .andExpect(jsonPath("$[0].priceInfo.pricingCategory").value(PriceCategory.DEFAULT.name()));
    }


    @Test
    public void testBookDetailEndpointShouldReturnUnAuthorizedStatus() throws Exception {
        bookControllerMock.perform(MockMvcRequestBuilders.get(BASE_URL+"/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testBookDetailEndpointShouldReturnSuccessStatus() throws Exception {
        Book someBook = new Book();
        someBook.setId(1);
        someBook.setName("android");
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setPricingCategory(PriceCategory.DEFAULT.name());
        someBook.setPriceInfo(priceInfo);
        given(bookService.getBookDetail(anyInt()))
                .willReturn(someBook);
        bookControllerMock.perform(MockMvcRequestBuilders.get(BASE_URL+"/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void shouldReturnBookDetailsForGivenId() throws Exception {

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateString = "2001-07-04T12:08:56.235-0700";
        Date expectedReleaseDate = df1.parse(dateString);
        String expectedDateString = DateUtil.toISO8601UTC(expectedReleaseDate);

        Book someBook = new Book();
        someBook.setId(1);
        someBook.setName("Android in Action, Second Edition");
        someBook.setReleaseDate(expectedReleaseDate);
        someBook.setAuthor("W. Frank Ableson");
        someBook.setShortDescription(null);
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setPricingCategory(PriceCategory.DEFAULT.name());
        someBook.setPriceInfo(priceInfo);
        given(bookService.getBookDetail(any()))
                .willReturn(someBook);

        bookControllerMock.perform(MockMvcRequestBuilders.get(BASE_URL+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Android in Action, Second Edition")))
                .andExpect(jsonPath("author", is("W. Frank Ableson")))
                .andExpect(jsonPath("releaseDate", is(expectedDateString)))
                .andExpect(jsonPath("shortDescription").doesNotExist())
                .andExpect(jsonPath("priceInfo.pricingCategory", is(PriceCategory.DEFAULT.name())));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void shouldThrowBookNotFoundExceptionForInvalidBookId() throws Exception {
        given(bookService.getBookDetail(1222))
                .willThrow(new BookNotFoundException());

        bookControllerMock.perform(MockMvcRequestBuilders.get(BASE_URL+"/1222"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", is("Book Id requested is not present.")));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testSearchBooksEndpoint() throws Exception {
        Book someBook = new Book();
        someBook.setId(1);
        someBook.setName("android");
        given(bookService.searchBooks(any(BookSearchParameter.class)))
                .willReturn(Arrays.asList(someBook));


        bookControllerMock.perform(MockMvcRequestBuilders.get(BASE_URL+"/search?author=&name=android&category="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name").value("android"));
    }


}
