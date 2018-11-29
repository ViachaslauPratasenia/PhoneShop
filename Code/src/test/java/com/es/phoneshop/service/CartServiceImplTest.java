package com.es.phoneshop.service;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.helping.Constants;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.CartService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.*;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceImplTest {
    private CartService cartService;
    private Cart cart;
    private int stockOne = 162, stockTwo = 44, stockThree = 63, quantityOne = 98, quantityTwo = 44, quantityThree = 198;
    private int newQuantityOne = 10, newQuantityTwo = 170;
    private int timeOne = 900, intervalOne = 1000, timeTwo = 1000, intervalTwo = 900;
    private Long productIdOne = 7L, productIdTwo = 10L;
    private Product product;
    private Product productOne, productTwo, productThree;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void init() {
        cartService = CartServiceImpl.getInstance();
        cart = new Cart();
        product = new Product();
        product.setStock(stockOne);
        product.setId(productIdOne);
        mockFields();
        setMockBehaviour();
    }

    @Test
    public void addProductQuantityLessEqualStockTest() throws CommonException {
        cartService.add(cart, productOne, quantityOne);
        cartService.add(cart, productTwo, quantityTwo);

        assertTrue(cart.getCartItems().get(0).equals(new CartItem(productOne, quantityOne)) && cart.getCartItems().get(1).equals(new CartItem(productTwo, quantityTwo)));
    }

    @Test(expected = CommonException.class)
    public void addProductQuantityGreaterStockTest() throws CommonException {
        cartService.add(cart,productThree,quantityThree);
    }

    @Test
    public void addEqualProductsTest() throws CommonException {
        cartService.add(cart, productOne, quantityTwo);
        cartService.add(cart, productOne, quantityTwo);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(cart.getCartItems().get(0).getProduct(), productOne);
    }

    @Test
    public void getCartCurrentSessionTest() throws CommonException {
        cartService.add(cart, productTwo, quantityTwo);
        CartItem cartItem = new CartItem(productTwo, stockTwo);
        setIsNewIntervalBehaviour(timeOne, intervalOne);

        CartItem compareItem = cartService.getCart(request).getCartItems().get(0);

        assertEquals(compareItem, cartItem);
    }

    @Test
    public void getCartNewSessionTest() throws CommonException {
        cartService.add(cart, productOne, quantityTwo);
        setIsNewIntervalBehaviour(timeTwo, intervalTwo);

        Cart tempCart = cartService.getCart(request);

        assertTrue(tempCart.getCartItems().isEmpty());
    }

    @Test(expected = CommonException.class)
    public void updateStockLessQuantityTest() throws CommonException {
        cartService.add(cart, product, quantityOne);

        cartService.update(cart, product, newQuantityTwo);
    }

    @Test
    public void updateQuantityLessStockTest() throws CommonException {
        cartService.add(cart, product, quantityOne);
        Integer tempStock = product.getStock();

        cartService.update(cart, product, newQuantityOne);

        assertEquals((Integer) (tempStock - newQuantityOne + quantityOne), product.getStock());
    }

    @Test(expected = CommonException.class)
    public void updateNotExistTest() throws CommonException {
        cartService.add(cart, productTwo, quantityOne);

        cartService.update(cart, product, newQuantityOne);
    }

    @Test(expected = CommonException.class)
    public void removeNotExistTest() throws CommonException {
        cartService.add(cart, product, quantityOne);

        cartService.remove(cart, productIdTwo);
    }

    @Test
    public void removeExistTest() throws CommonException {
        cartService.add(cart, product, quantityOne);

        cartService.remove(cart, productIdOne);

        assertTrue(cart.getCartItems().isEmpty());
    }

    @After
    public void destroy() {
        cartService = null;
        cart = null;
        productOne = null;
        productTwo = null;
        productThree = null;
        product = null;
    }

    private void mockFields() {
        productOne = Mockito.mock(Product.class);
        productTwo = Mockito.mock(Product.class);
        productThree = Mockito.mock(Product.class);
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
    }

    private void setMockBehaviour() {
        Mockito.when(productOne.getStock()).thenReturn(stockOne);
        Mockito.when(productTwo.getStock()).thenReturn(stockTwo);
        Mockito.when(productThree.getStock()).thenReturn(stockThree);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute(Constants.CART_ATTRIBUTE_NAME)).thenReturn(cart);
        Mockito.when(productTwo.getId()).thenReturn(productIdTwo);
    }

    private void setIsNewIntervalBehaviour(int time, int interval) {
        Mockito.when(session.getMaxInactiveInterval()).thenReturn(interval);
        boolean isNew = (time > session.getMaxInactiveInterval());
        Mockito.when(session.isNew()).thenReturn(isNew);
    }

}