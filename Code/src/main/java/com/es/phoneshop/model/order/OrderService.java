package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderService {
    public Order placeOrder(Cart cart, String name, String address, String phone, BigDecimal totalCost);
    public Optional<Order> getOrder(String orderId);
}
