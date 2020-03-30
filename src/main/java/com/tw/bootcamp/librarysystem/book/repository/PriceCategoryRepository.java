package com.tw.bootcamp.librarysystem.book.repository;

import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.model.PriceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Integer>, JpaSpecificationExecutor<Book> {



}
