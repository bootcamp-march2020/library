package com.tw.bootcamp.librarysystem.book.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.tw.bootcamp.librarysystem.book.model.Views;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceInfoDTO {

    @JsonView(Views.List.class)
    private String pricingCategory;

    @JsonView(Views.List.class)
    private Integer initialPricingPeriod;

    @JsonView(Views.List.class)
    private Double initialPrice;

    @JsonView(Views.List.class)
    private Double pricePerWeek;

    @JsonView(Views.List.class)
    private Double pricePerDay;

    public String getPricingCategory() {
        return pricingCategory;
    }

    public void setPricingCategory(String pricingCategory) {
        this.pricingCategory = pricingCategory;
    }

    public Integer getInitialPricingPeriod() {
        return initialPricingPeriod;
    }

    public void setInitialPricingPeriod(Integer initialPricingPeriod) {
        this.initialPricingPeriod = initialPricingPeriod;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getPricePerWeek() {
        return pricePerWeek;
    }

    public void setPricePerWeek(Double pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
