package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.*;
import com.es.phoneshop.model.helping.ApplicationMessage;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.parser.AttributeParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.model.helping.Constants.*;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();
    private CartService cartService = CartServiceImpl.getInstance();
    private AttributeParser parser = new AttributeParser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        handleSession(request);
        Product product = null;

        try {
            product = productDao.getProduct(getProductId(request));
            setAttributes(request, product);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (CommonException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        setAttributes(request, product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long productId = getProductId(request);
        Product product = null;
        Integer messageCode = ApplicationMessage.DEFAULT_CODE.getCode();
        String quantityString = (String) request.getParameter(QUANTITY_ATTRIBUTE_NAME);
        try {
            messageCode = executeOperations(request, product, productId, messageCode, quantityString);
        } catch (CommonException e) {
            messageCode = e.getCode();
        }

        setSessionAttributes(request, messageCode, quantityString);
        response.sendRedirect(request.getRequestURI());
    }

    private void handleSession(HttpServletRequest request) {
        if (request.getSession().getAttribute(QUANTITY_ATTRIBUTE_NAME) != null) {

            request.setAttribute(MESSAGE_CODE_ATTRIBUTE_NAME, request.getSession().getAttribute(MESSAGE_CODE_ATTRIBUTE_NAME));
            request.setAttribute(QUANTITY_ATTRIBUTE_NAME, request.getSession().getAttribute(QUANTITY_ATTRIBUTE_NAME));

        }
        setSessionAttributes(request, ApplicationMessage.DEFAULT_CODE.getCode(), null);

    }

    private void setSessionAttributes(HttpServletRequest request, Integer code, String quantity) {
        request.getSession().setAttribute(MESSAGE_CODE_ATTRIBUTE_NAME, code);
        request.getSession().setAttribute(QUANTITY_ATTRIBUTE_NAME, quantity);
    }

    private void setAttributes(HttpServletRequest request, Product product) {

        request.setAttribute(PRODUCT_ATTRIBUTE_NAME, product);

        request.setAttribute(SUCCESS_HEAD_ATTRIBUTE_NAME, ApplicationMessage.SUCCESS_HEAD.getCode());
        request.setAttribute(ERROR_HEAD_ATTRIBUTE_NAME, ApplicationMessage.ERROR_HEAD.getCode());
        request.setAttribute(SUCCESS_ATTRIBUTE_NAME, ApplicationMessage.SUCCESS.getCode());
        request.setAttribute(DEFAULT_CODE_ATTRIBUTE_NAME, ApplicationMessage.DEFAULT_CODE.getCode());
        request.setAttribute(BUNDLE_NAMES_ATTRIBUTE_NAME,ApplicationMessage.bundleNames);

    }

    private Long getProductId(HttpServletRequest request) throws NumberFormatException, StringIndexOutOfBoundsException {

        String path = request.getPathInfo();
        if (path == null || path.compareTo("/") == 0) {
            throw new StringIndexOutOfBoundsException();
        }
        int index = path.indexOf("/") + 1;
        path = path.substring(index);
        return Long.valueOf(path);
    }

    private Integer executeOperations(HttpServletRequest request, Product product, Long productId, Integer message, String quantityString) throws CommonException {

        Integer quantity = parser.parseAttribute(request, quantityString);
        product = productDao.getProduct(productId);
        cartService.add(cartService.getCart(request), product, quantity);
        message = ApplicationMessage.SUCCESS.getCode();
        return message;
    }
}