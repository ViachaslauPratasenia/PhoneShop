package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.helping.ApplicationMessage;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.parser.AttributeParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.es.phoneshop.model.helping.Constants.*;

public class CartPageServlet extends HttpServlet {

    private CartService cartService = CartServiceImpl.getInstance();
    private AttributeParser parser = new AttributeParser();
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setMaxInactiveInterval(MAX_SESSION_TIMEOUT_IN_SECONDS);

        handleSession(request);

        setRemoveAttribute(request);

        setSessionAttributes(request, false, false, null, null);

        setAttributes(request);

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean isRemove = false;

        Optional<CartItem> item = cartService.getCart(request).getCartItems().stream()
                .filter(cartItem -> request.getParameter(COMMON_REMOVE_BUTTONS_STRING + cartItem.getProduct().getId()) != null).findAny();
        if (item.isPresent()) {
            try {
                cartService.remove(cartService.getCart(request), item.get().getProduct().getId());
            } catch (CommonException ignored) {

            }
            isRemove = true;
        } else {
            Boolean success = true;

            String[] productIds = request.getParameterValues(PRODUCT_ID_ATTRIBUTE_NAME);
            String[] newQuantities = request.getParameterValues(NEW_QUANTITY_ATTRIBUTE_NAME);
            Integer[] errors = new Integer[productIds.length];

            for (int i = 0; i < productIds.length; i++) {
                try {
                    updateCart(request, productIds, newQuantities, i);
                } catch (NumberFormatException e) {
                    errors[i] = ApplicationMessage.NOT_NUMBER.getCode();
                    success = false;
                } catch (CommonException e) {
                    errors[i] = e.getCode();
                    success = false;
                }
            }
            setSessionAttributes(request, isRemove, success, errors, newQuantities);
        }

        request.getSession().setAttribute(REMOVE_ATTRIBUTE_NAME, isRemove);
        response.sendRedirect(request.getRequestURI());
    }

    private void setSessionAttributes(HttpServletRequest request, Boolean isRemove, Boolean isSuccess, Integer[] errors, String[] newQuantities) {
        ArrayList<Integer> errorList = null;
        ArrayList<String> quantityList = null;

        if (errors != null && newQuantities != null) {
            errorList = new ArrayList<>(Arrays.asList(errors));
            quantityList = new ArrayList<>(Arrays.asList(newQuantities));
        }

        request.getSession().setAttribute(REMOVE_ATTRIBUTE_NAME, isRemove);
        request.getSession().setAttribute(SUCCESS_ATTRIBUTE_NAME, isSuccess);
        request.getSession().setAttribute(ERRORS_ATTRIBUTE_NAME, errorList);
        request.getSession().setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME, quantityList);
    }

    private void handleSession(HttpServletRequest request) {
        if (request.getSession().getAttribute(ERRORS_ATTRIBUTE_NAME) != null) {
            request.setAttribute(SUCCESS_ATTRIBUTE_NAME, request.getSession().getAttribute(SUCCESS_ATTRIBUTE_NAME));
            request.setAttribute(ERRORS_ATTRIBUTE_NAME, request.getSession().getAttribute(ERRORS_ATTRIBUTE_NAME));
            request.setAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME, request.getSession().getAttribute(NEW_QUANTITIES_ATTRIBUTE_NAME));
        }
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute(SUCCESS_HEAD_ATTRIBUTE_NAME, ApplicationMessage.SUCCESS_HEAD.getCode());
        request.setAttribute(CART_UPDATE_SUCCESS, ApplicationMessage.CART_UPDATE_SUCCESS.getCode());
        request.setAttribute(CART_ITEM_REMOVE_SUCCESS, ApplicationMessage.CART_ITEM_REMOVE_SUCCESS.getCode());
        request.setAttribute(CART_ATTRIBUTE_NAME, cartService.getCart(request));
        request.setAttribute(BUNDLE_NAMES_ATTRIBUTE_NAME,ApplicationMessage.bundleNames);
    }

    private void setRemoveAttribute(HttpServletRequest request) {
        Boolean remove = (Boolean) request.getSession().getAttribute(REMOVE_ATTRIBUTE_NAME);
        if (remove != null && !remove.equals(false)) {
            request.setAttribute(REMOVE_ATTRIBUTE_NAME, request.getSession().getAttribute(REMOVE_ATTRIBUTE_NAME));
        }
    }

    private void updateCart(HttpServletRequest request, String[] productIds, String[] newQuantities, int index) throws CommonException, NumberFormatException {
        Product product = productDao.getProduct(Long.valueOf(productIds[index]));
        Integer quantity = parser.parseAttribute(request, newQuantities[index]);
        cartService.update(cartService.getCart(request), product, quantity);
    }


}
