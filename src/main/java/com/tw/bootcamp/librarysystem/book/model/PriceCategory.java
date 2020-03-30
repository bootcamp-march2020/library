package com.tw.bootcamp.librarysystem.book.model;

import javax.persistence.*;

@Entity
@Table(name = "PRICE_CATEGORY")
public class PriceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

}
