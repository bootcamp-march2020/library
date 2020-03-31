package com.tw.bootcamp.librarysystem.book.repository.integration;

import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.model.PriceCategory;
import com.tw.bootcamp.librarysystem.book.model.SearchCriteria;
import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
import com.tw.bootcamp.librarysystem.book.repository.BookSpecification;
import com.tw.bootcamp.librarysystem.book.repository.PriceInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@DataJpaTest
public class BookRepositoryIntegrationTest {
    @Autowired
    private BookRepository bookRepository;
    

    @Test
    public void findBookWithBookNameAndAuthorNameTest() {
        BookSpecification spec1 =
                new BookSpecification(new SearchCriteria("name", "my book"));
        BookSpecification spec2 =
                new BookSpecification(new SearchCriteria("author", "author"));
        List<Book> results = bookRepository.findAll(Specification.where(spec1).and(spec2));
        assertEquals(1, results.size());
        assertEquals("author", results.get(0).getAuthor());
        assertEquals(PriceCategory.DEFAULT.name(), results.get(0).getPriceInfo().getPricingCategory());
    }
}