package com.tw.bootcamp.librarysystem.book.model;

import javax.persistence.*;

@Entity
@Table(name = "PRICE_INFO")
public class PriceInfo {

    @Id
    @Column(name = "pricing_category")
    private String pricingCategory;

    @Column(name = "initial_pricing_period")
    private Integer initialPricingPeriod;

    @Column(name = "initial_price")
    private Double initialPrice;

    @Column(name = "price_per_week")
    private Double pricePerWeek;

    @Column(name = "price_per_day")
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
