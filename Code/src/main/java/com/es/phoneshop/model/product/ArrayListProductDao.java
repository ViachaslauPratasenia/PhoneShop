
package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.helping.ApplicationMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static Long currentId = 0L;
    private List<Product> products;

    private ArrayListProductDao() {
        this.products = new ArrayList<Product>();
    }

    private static class ArrayListProductDaoHelper {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    public static ArrayListProductDao getInstance() {
        return ArrayListProductDaoHelper.INSTANCE;
    }

    public List<Product> findProducts() {
        return products.stream().filter(product -> product.getPrice() != null && product.getStock() != null && product.getStock() > 0 ).collect(Collectors.toList());
    }

    public Product getProduct(Long id) throws CommonException {
        for(Product currentProduct : products) {
            if(currentProduct.getId().compareTo(id) == 0) {
                return currentProduct;
            }
        }
        throw new CommonException(ApplicationMessage.NOT_FOUND);
    }

    public void save(Product product) {
        ArrayListProductDao.currentId++;
        product.setId(ArrayListProductDao.currentId);
        products.add(product);
    }

    public void remove(Long id) throws CommonException {
        for(Product currentProduct : products) {
            if(currentProduct.getId().compareTo(id) == 0) {
                products.remove(currentProduct);
                return;
            }
        }
        throw new CommonException(ApplicationMessage.NOT_FOUND);
    }

    public void pushDefaultProducts() {
        save(new Product("Apple iPhone X","New",BigDecimal.valueOf(999.99),
                Currency.getInstance(Locale.US),35));
        save(new Product("Huawei P20 Pro'","Brand New",BigDecimal.valueOf(699.99),
                Currency.getInstance(Locale.US),40));
        save(new Product("Nokia 7 plus","Absolutely Fantastic!",BigDecimal.valueOf(449.99),
                Currency.getInstance(Locale.US),33));
        save(new Product("Sony XZ2 Premium","N/D",BigDecimal.valueOf(599.99),
                Currency.getInstance(Locale.US),23));
        save(new Product("Motorola ZZ 11","Delicious",null,
                Currency.getInstance(Locale.US),1));
        save(new Product("Honor 10","Why not?",BigDecimal.valueOf(699.99),
                Currency.getInstance(Locale.US),50));
    }

    public void clearAll() {
        products.clear();
        currentId = 0L;
    }
}
