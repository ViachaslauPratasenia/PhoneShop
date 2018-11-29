package com.es.phoneshop.model.order;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractOrderItem implements Serializable {

    private Product product;
    private Integer quantity;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public AbstractOrderItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public AbstractOrderItem() {
        this.product = new Product();
        this.quantity = 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.product, this.quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AbstractOrderItem orderItem = (AbstractOrderItem) obj;
        return ((this.product == null && orderItem.product == null) || (this.product != null && this.product.equals(orderItem.product)));
    }
}
