package com.es.phoneshop.web;

import com.es.phoneshop.model.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDAO = ArrayListProductDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productDAO.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> productList = new ArrayList<>();
        String searchValue = request.getParameter("search");

        if(searchValue != null){
            String searchQuery = request.getParameter("searchQuery");
            for(Product product : productDAO.findProducts()){
                if(product.getDescription().equals(searchQuery) || product.getCode().equals(searchQuery)){
                    productList.add(product);
                }
            }
            request.setAttribute("products", productList);
            response.sendRedirect(request.getRequestURI() + "?searchQuery=" + searchQuery);
        }
        else {
            doGet(request, response);
        }
    }
}
