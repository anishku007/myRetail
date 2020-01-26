package com.myRetail.productservice.modal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceData {
    @JsonProperty("value")
    protected String price;

    @JsonProperty("currency_code")
    protected String currencyCode;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public PriceData() {
    }

    public PriceData(String price, String currencyCode) {
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
