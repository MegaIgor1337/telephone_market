package dao;

import entity.brand.Brand;
import entity.color.Color;
import entity.country.Country;
import entity.model.Model;
import entity.product.Product;
import dao.filter.ProductFilter;
import junit.framework.TestCase;

import java.math.BigDecimal;

public class ProductDaoTest extends TestCase {

    private static final ProductDao productDao = ProductDao.getInstance();

    public void testUpdate() {
        Product product = new Product(
                12L,
                new Brand(6L, "Honor"),
                new Model(36L, "X7"),
                new Color(6L, "Blue-Green"),
                new Country(1L, "Russia"),
                22,
                BigDecimal.valueOf(240.00)
        );
        assertTrue(productDao.update(product));
    }

    public void testFindById() {
        assertEquals(BigDecimal.valueOf(300.00).setScale(2), productDao.findById(10L).get().getCost());
    }

    public void testFindAll() {
        ProductFilter filter = new ProductFilter(
                5, 0, null, null, null,
                new Country(1L, "Russia"), null, null);
        assertEquals(2, productDao.findAll(filter).size());
    }

}