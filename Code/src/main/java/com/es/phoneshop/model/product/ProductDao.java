package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.CommonException;

import java.util.List;

public interface ProductDao {

    public List<Product> findProducts();

    public Product getProduct(Long id) throws CommonException;

    public void save(Product product);

    public void remove(Long id) throws CommonException;

    public void clearAll();
}
