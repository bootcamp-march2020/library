package com.tw.bootcamp.librarysystem.migration;

import com.tw.bootcamp.librarysystem.book.model.PriceCategory;
import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
import com.tw.bootcamp.librarysystem.book.repository.PriceCategoryRepository;
import db.migration.V3__BookMigrationUtil;
import org.flywaydb.core.api.migration.Context;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MigrationIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PriceCategoryRepository priceCategoryRepository;

    private Connection testDBConnection;

    private V3__BookMigrationUtil bookMigrationUtil;

    private RestTemplate mockRestTemplate;

    @BeforeEach
    public void setup() throws SQLException {
        mockRestTemplate = Mockito.mock(RestTemplate.class);
        bookMigrationUtil = new V3__BookMigrationUtil(mockRestTemplate);
        testDBConnection = dataSource.getConnection();
    }

    @Test
    public void migrateBooksTest() throws Exception {
        Context mockFlywayContext = Mockito.mock(Context.class);
        List<Map<String, Object>> sampleBooks = getSampleBooks();

        when(mockFlywayContext.getConnection()).thenReturn(testDBConnection);
        when(mockRestTemplate.exchange(anyString(), Mockito.eq(HttpMethod.GET),
                Mockito.eq(null), Mockito.eq(new ParameterizedTypeReference<List>() {
                })))
                .thenReturn(ResponseEntity.accepted().body(sampleBooks));

        bookMigrationUtil.migrate(mockFlywayContext);
        List<String> bookNames = bookRepository.findAll().stream().map(book -> book.getName()).collect(Collectors.toList());
        assertTrue(bookNames.contains("Book1"));
        assertTrue(bookNames.contains("Book2"));
    }

    @Test
    public void priceCategoryDatasetupTest() {
        List<PriceCategory> priceCategories = priceCategoryRepository.findAll();
        assertTrue(priceCategories.size() == 2);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getSampleBooks() {
        List<Map<String, Object>> books = new ArrayList<>();
        Map<String, Object> book1 = new HashMap<>();
        book1.put("title", "Book1");
        book1.put("thumbnailUrl", "Book1Thumbnail");
        book1.put("isbn", "123456789");
        book1.put("shortDescription", "shortdescription");
        book1.put("authors", Arrays.asList("author1"));
        book1.put("publishedDate", new HashMap<String, String>());
        book1.put("category", Arrays.asList("action", "drama"));
        ((Map) book1.get("publishedDate")).put("date", "2009-04-01T00:00:00.000-0700");
        books.add(book1);

        Map<String, Object> book2 = new HashMap<>();
        book2.put("title", "Book2");
        book2.put("isbn", "23455678");
        book2.put("shortDescription", "BookShortDescription");
        book2.put("thumbnailUrl", "Book2Thumbnail");
        book2.put("authors", Arrays.asList("author2", "author3"));
        book2.put("category", Arrays.asList("fiction", "horror"));
        book2.put("publishedDate", new HashMap<String, String>());
        ((Map) book2.get("publishedDate")).put("date", "2009-04-01T00:00:00.000-0700");
        books.add(book2);
        return books;
    }
}
