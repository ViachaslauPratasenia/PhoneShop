package com.es.phoneshop.dao;

import com.es.phoneshop.exception.CommonException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private final String codes[] = {"RCLV-LNZS", "5KGS-49BV", "E4DZ-XDRZ", "GFD8-UD57", "WEA8-REWQ", "ZYP7-TKLP", "2A4X-RGG6", "WDNR-VPX6", "WD4W-65YZ", "K6EV-SWAQ"};
    private final String descriptions[] = {"Willing", "Bent", "Temporary", "Sudden", "Excited", "Ultra", "Nimble", "Shrill", "Tough", "Hysterical"};
    private final BigDecimal prices[] = {
            null,
            null,
            null,
            BigDecimal.valueOf(393.10),
            BigDecimal.valueOf(-861.20),
            BigDecimal.valueOf(418.20),
            BigDecimal.valueOf(0),
            BigDecimal.valueOf(-134.62),
            BigDecimal.valueOf(433.71),
            BigDecimal.valueOf(2326)
    };
    private final Integer stockArray[] = {-35, 0, 17, -31, 0, 3, 79, 74, 41, 135};
    private Product productArray[] = null;

    @Before
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        productDao.clearAll();
        productArray = new Product[codes.length];
        for (int i = 0; i < codes.length; i++) {
            createProduct(i);
        }
    }

    @Test
    public void findProductsEmptyListTest() {
        List<Product> products = productDao.findProducts();

        assertTrue(products.isEmpty());
    }

    @Test
    // First 5 products are not appropriate
    public void findProductsOnlyBadDataTest() {
        int badDataAmount = 5;
        for (int i = 0; i < 5; i++) {
            productDao.save(productArray[i]);
        }

        List<Product> products = productDao.findProducts();

        assertTrue(products.isEmpty());
    }

    @Test
    // Next 5 products are appropriate
    public void findProductsOnlyGoodDataTest() {
        int goodDataAmount = 5;
        int startIndex = 5;
        for (int i = startIndex; i < startIndex + goodDataAmount; i++) {
            productDao.save(productArray[i]);
        }

        List<Product> products = productDao.findProducts();

        assertEquals(products.size(), goodDataAmount);
    }

    @Test
    // 3 products are not appropriate,but 4 ones are
    public void findProductsBothDataTypeTest() {
        int badIndices[] = {0, 1, 3};
        int goodIndices[] = {5, 7, 8, 9};
        for (int badIndex : badIndices) {
            productDao.save(productArray[badIndex]);
        }
        for (int goodIndex : goodIndices) {
            productDao.save(productArray[goodIndex]);
        }

        List<Product> products = productDao.findProducts();

        assertEquals(products.size(), goodIndices.length);
    }

    @Test
    public void getProductPresentIdTest() throws CommonException {
        int productIndices[] = {2, 7, 9};
        long presentId = 2;
        int index = 7;
        Product product = productArray[index];
        for (int i : productIndices) {
            productDao.save(productArray[i]);
        }

        Product compareProduct = productDao.getProduct(presentId);

        assertEquals(compareProduct, product);
    }

    @Test(expected = CommonException.class)
    public void getProductAbsentIdTest() throws CommonException {
        int productIndices[] = {1, 3, 5, 9};
        long absentId = 5;
        for (int i : productIndices) {
            productDao.save(productArray[i]);
        }

        Product product = productDao.getProduct(absentId);
    }

    @Test
    public void saveTest() throws CommonException {
        int index = 4;
        Product product = productArray[index];

        productDao.save(product);

        assertEquals(productDao.getProduct(1L), product);
    }

    @Test(expected = CommonException.class)
    public void removeExistedTest() throws CommonException {
        int indices[] = {6, 7, 8, 9};
        for (int i : indices) {
            productDao.save(productArray[i]);
        }
        long deleteId = 3;

        productDao.remove(deleteId);

        Product deletedProduct = productDao.getProduct(deleteId);
    }

    @Test(expected = CommonException.class)
    public void removeNotExistedTest() throws CommonException {
        int indices[] = {1, 4, 5, 9};
        for (int i : indices) {
            productDao.save(productArray[i]);
        }
        long deleteId = 5;

        productDao.remove(deleteId);
    }

    @After
    public void destroy() {
        productDao = null;
        productArray = null;
    }

    private void createProduct(Integer index) {
        productArray[index] = new Product(codes[index], descriptions[index], prices[index], Currency.getInstance(Locale.getDefault()), stockArray[index]);
    }
}
