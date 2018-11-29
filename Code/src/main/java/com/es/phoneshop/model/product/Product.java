package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class Product implements Serializable {

    private Long id;
    private String code;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private Integer stock;

    public Product() {
        this.code = "";
        this.description = "";
        this.price = new BigDecimal(0);
        this.currency = Currency.getInstance(Locale.getDefault());
        this.stock = 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.code, this.description, this.price, this.currency, this.stock);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        return ((this.id == null && product.id == null) || (this.id != null && this.id.equals(product.id)))
                && ((this.code == null && product.code == null) || (this.code != null && this.code.equals(product.code)))
                && ((this.description == null && product.description == null) || (this.description != null && this.description.equals(product.description)))
                && ((this.price == null && product.price == null) || (this.price != null && this.price.equals(product.price)))
                && ((this.currency == null && product.currency == null) || (this.currency != null && this.currency.equals(product.currency)));
    }

    public Product(String code, String description, BigDecimal price, Currency currency, Integer stock) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Integer getStock() {
        return stock;
    }
}
