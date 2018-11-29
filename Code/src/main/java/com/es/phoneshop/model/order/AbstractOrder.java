package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOrder<T extends AbstractOrderItem>   {
    private List<T> cartItems;

    public AbstractOrder(List<T> cartItems) {
        this.cartItems = cartItems;
    }

    public void setCartItems(List<T> cartItems) {
        this.cartItems = cartItems;
    }

    public List<T> getCartItems() {
        return cartItems;
    }

    public AbstractOrder() {
        this.cartItems = new ArrayList<>();
    }

    public void clear() {
        cartItems.clear();
    }
}
