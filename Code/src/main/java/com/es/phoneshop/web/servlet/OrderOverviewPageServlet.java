package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.helping.ApplicationMessage;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderServiceImpl;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.es.phoneshop.model.helping.Constants.CURRENCY_ATTRIBUTE_NAME;
import static com.es.phoneshop.model.helping.Constants.ORDER_ATTRIBUTE_NAME;

public class OrderOverviewPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String pathOrderId = getOrderId(request);
            OrderService orderService = OrderServiceImpl.getInstance();
            Optional<Order> optionalOrder = orderService.getOrder(pathOrderId);

            if(optionalOrder.isPresent()) {
                request.setAttribute(ORDER_ATTRIBUTE_NAME,optionalOrder.get());
                request.setAttribute(CURRENCY_ATTRIBUTE_NAME,optionalOrder.get().getCartItems().get(0).getProduct().getCurrency());
                request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request,response);
            }
            else throw new CommonException(ApplicationMessage.NOT_FOUND);

        }
        catch (StringIndexOutOfBoundsException | CommonException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String getOrderId(HttpServletRequest request) throws StringIndexOutOfBoundsException {

        String path = request.getPathInfo();
        if (path == null || path.compareTo("/") == 0) {
            throw new StringIndexOutOfBoundsException();
        }
        int index = path.indexOf("/") + 1;
        path = path.substring(index);
        return path;
    }
}
