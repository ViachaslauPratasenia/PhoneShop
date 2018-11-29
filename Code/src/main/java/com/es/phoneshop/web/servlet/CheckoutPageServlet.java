package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.helping.ApplicationMessage;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static com.es.phoneshop.model.helping.Constants.*;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService = CartServiceImpl.getInstance();
    private Currency currency = Currency.getInstance(Locale.US);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        if(!cart.getCartItems().isEmpty()) {
            currency = cart.getCartItems().get(0).getProduct().getCurrency();
        }

        handleSession(request);
        setAttributes(request,cart);

        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter(NAME_ATTRIBUTE_NAME);
        String address = request.getParameter(ADDRESS_ATTRIBUTE_NAME);
        String phone = request.getParameter(PHONE_ATTRIBUTE_NAME);

        Boolean isError = (name.isEmpty() || address.isEmpty() || phone.isEmpty());
        if(isError) {
            setSessionAttributes(request,isError,name,address,phone);
            response.sendRedirect(request.getRequestURI());
        }
        else  {
            Cart cart = cartService.getCart(request);
            Order order = OrderServiceImpl.getInstance().placeOrder(cart,name,address,phone,calculateTotalCost(cart));
            response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getOrderId());
        }
    }

    private void handleSession(HttpServletRequest request) {
        Boolean isError = (Boolean)request.getSession().getAttribute(IS_ERROR_ATTRIBUTE_NAME);
        if(isError != null) {
            request.setAttribute(IS_ERROR_ATTRIBUTE_NAME,isError);
            request.setAttribute(NAME_ATTRIBUTE_NAME,request.getSession().getAttribute(NAME_ATTRIBUTE_NAME));
            request.setAttribute(ADDRESS_ATTRIBUTE_NAME,request.getSession().getAttribute(ADDRESS_ATTRIBUTE_NAME));
            request.setAttribute(PHONE_ATTRIBUTE_NAME,request.getSession().getAttribute(PHONE_ATTRIBUTE_NAME));
        }
        setSessionAttributes(request,false,null,null,null);
    }

    private void setAttributes(HttpServletRequest request,Cart cart) {
        request.setAttribute(CURRENCY_ATTRIBUTE_NAME,currency);
        request.setAttribute(CART_ATTRIBUTE_NAME,cart);
        request.setAttribute(TOTAL_ATTRIBUTE_NAME,calculateTotalCost(cart));
        request.setAttribute(BUNDLE_NAMES_ATTRIBUTE_NAME,ApplicationMessage.bundleNames);
        request.setAttribute(ERROR_HEAD_ATTRIBUTE_NAME,ApplicationMessage.ERROR_HEAD.getCode());
        request.setAttribute(ERROR_ATTRIBUTE_NAME,ApplicationMessage.EMPTY_FIELDS.getCode());
    }

    private void setSessionAttributes(HttpServletRequest request,Boolean isError,String name,String address,String phone) {
        request.getSession().setAttribute(IS_ERROR_ATTRIBUTE_NAME,isError);
        request.getSession().setAttribute(NAME_ATTRIBUTE_NAME,name);
        request.getSession().setAttribute(ADDRESS_ATTRIBUTE_NAME,address);
        request.getSession().setAttribute(PHONE_ATTRIBUTE_NAME,phone);
    }

    private BigDecimal calculateTotalCost(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal quantity;
        BigDecimal price;
        for(CartItem item : cart.getCartItems()) {
            quantity = BigDecimal.valueOf(item.getQuantity());
            price = item.getProduct().getPrice();
            total = total.add(price.multiply(quantity));
        }
        return total;
    }
}
