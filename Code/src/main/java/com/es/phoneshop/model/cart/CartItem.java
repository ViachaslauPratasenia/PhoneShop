package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.order.AbstractOrderItem;
import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.Objects;

public class CartItem extends AbstractOrderItem implements Serializable {
    public CartItem(Product product,Integer quantity) {
        super(product,quantity);
    }
    public CartItem() {
        super();
    }
}
