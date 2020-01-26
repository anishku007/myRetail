package com.myRetail.productservice.modal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "pid",unique=true,nullable = false)
    private String pid;
    private String title;
    @Column(name="price")
    private Double currentPrice;
    @Column(name="currencyCode")
    private String currency_code;
    public Product() {
    }
    public Product(String pid) {
        super();
        this.pid = pid;
    }
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(pid, product.pid) &&
                Objects.equals(title, product.title) &&
                Objects.equals(currentPrice, product.currentPrice) &&
                Objects.equals(currency_code, product.currency_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, title, currentPrice, currency_code);
    }
}
