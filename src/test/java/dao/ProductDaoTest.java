package dao;

import entity.brand.Brand;
import entity.color.Color;
import entity.country.Country;
import entity.model.Model;
import entity.product.Product;
import filter.ProductFilter;
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

    public void testSave() {
        Product product = new Product(
                12L,
                new Brand(7L, "Vivo+"),
                new Model(46L, "T1"),
                new Color(6L, "Blue-Green"),
                new Country(1L, "Russia"),
                22,
                BigDecimal.valueOf(240.00)
        );
        productDao.save(product);
        assertEquals(productDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(productDao.findAll().size() - 1).getId(), product.getId());
    }

    public void testDelete() {
        assertTrue(productDao.delete(productDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(productDao.findAll().size() - 1).getId()));
    }
}