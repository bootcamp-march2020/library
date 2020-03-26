package com.tw.bootcamp.librarysystem.book.repository.integration;

import com.tw.bootcamp.librarysystem.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertEquals;

@DataJpaTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void test() {
        assertEquals(1, bookRepository.findAll().size());
    }
}