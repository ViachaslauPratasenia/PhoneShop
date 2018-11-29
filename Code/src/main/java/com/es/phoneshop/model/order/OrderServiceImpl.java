package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private List<Order> orders;

    private OrderServiceImpl() {
        orders = new ArrayList<>();
    }
    private static class OrderServiceHelper {
        private static final OrderService INSTANCE = new OrderServiceImpl();
    }
    public static OrderService getInstance() {
        return OrderServiceImpl.OrderServiceHelper.INSTANCE;
    }

    @Override
    public Order placeOrder(Cart cart, String name, String address, String phone, BigDecimal totalCost) {
        Order order = new Order();
        order.setName(name);
        order.setAddress(address);
        order.setPhone(phone);
        order.setOrderId(generateOrderId());
        order.setTotalCost(totalCost);

        order.setCartItems(cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            return orderItem;
        } ).collect(Collectors.toList()));
        cart.clear();
        orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> getOrder(String orderId) {
        return this.orders.stream().filter(order -> order.getOrderId().equals(orderId)).findAny();
    }

    private String generateOrderId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }


}
