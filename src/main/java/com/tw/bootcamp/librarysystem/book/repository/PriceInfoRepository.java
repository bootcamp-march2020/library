package com.tw.bootcamp.librarysystem.book.repository;

import com.tw.bootcamp.librarysystem.book.model.Book;
import com.tw.bootcamp.librarysystem.book.model.PriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Integer>, JpaSpecificationExecutor<PriceInfo> {



}
