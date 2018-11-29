package com.es.phoneshop.web.listener;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SampleDataServletContextListener implements ServletContextListener {

    private ProductDao productDao;
    private CartServiceImpl cartServiceImpl;
    private static final String INIT_PARAMETER_NAME = "init";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Boolean isInitialized;
        ServletContext servletContext = servletContextEvent.getServletContext();
        String initString = (String) servletContext.getInitParameter(SampleDataServletContextListener.INIT_PARAMETER_NAME);
        isInitialized = Boolean.parseBoolean(initString);
        if(isInitialized) {
            productDao = ArrayListProductDao.getInstance();
            ((ArrayListProductDao) productDao).pushDefaultProducts();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}