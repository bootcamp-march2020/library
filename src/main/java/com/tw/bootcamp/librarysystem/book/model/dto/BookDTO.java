package com.tw.bootcamp.librarysystem.book.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.tw.bootcamp.librarysystem.book.model.Views;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    @JsonView(Views.List.class)
    private int id;

    @JsonView(Views.List.class)
    private String name;

    private String coverPicture;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private Date releaseDate;

    @JsonView(Views.List.class)
    private String author;

    @JsonView(Views.List.class)
    private String shortDescription;

    private String isbn;

    @JsonView(Views.List.class)
    private String category;

    @JsonView(Views.List.class)
    private PriceInfoDTO priceInfo;

    public String getName() {
        return name;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getCategory() {
        return category;
    }

    public PriceInfoDTO getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfoDTO priceInfo) {
        this.priceInfo = priceInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
