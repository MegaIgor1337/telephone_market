package dao;

import entity.address.Address;
import entity.brand.Brand;
import entity.client.Client;
import entity.color.Color;
import entity.country.Country;
import entity.model.Model;
import entity.order.Order;
import entity.orderproduct.OrderProduct;
import entity.orderproduct.PrimaryKeyOrderProduct;
import entity.product.Product;
import filter.OrderProductFilter;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderProductDaoTest extends TestCase {

    private static final OrderProductDao orderProductDao = OrderProductDao.getInstance();

    public void testUpdate() {
        OrderProduct orderProduct = new OrderProduct(
                new PrimaryKeyOrderProduct(
                        new Order(
                                3L,
                                new Client(
                                        3L,
                                        "Gennadiy22",
                                        "boring",
                                        "HH333222",
                                        new Address(
                                                3L,
                                                "Belarus",
                                                "Grodno",
                                                "Oginskogo",
                                                "4a",
                                                "14"
                                        ),
                                        "goodjob@gmail.com"
                                ),
                                BigDecimal.valueOf(215.00),
                                LocalDateTime.of(2021, 11, 12, 18, 37, 0),
                                true,
                                LocalDateTime.of(2021, 11, 18, 14, 0, 0)
                        ),
                        new Product(
                                2L,
                                new Brand(2L, "Apple"),
                                new Model(6L, "11"),
                                new Color(5L, "Rose Gold"),
                                new Country(4L, "France"),
                                20,
                                BigDecimal.valueOf(150.00)
                        )

                ),
                6

        );
        assertTrue(orderProductDao.update(orderProduct));
    }

    public void testFindById() {
        assertEquals(6, orderProductDao.findById(new PrimaryKeyOrderProduct(
                new Order(
                        3L,
                        new Client(
                                3L,
                                "Gennadiy22",
                                "boring",
                                "HH333222",
                                new Address(
                                        3L,
                                        "Belarus",
                                        "Grodno",
                                        "Oginskogo",
                                        "4a",
                                        "14"
                                ),
                                "goodjob@gmail.com"
                        ),
                        BigDecimal.valueOf(215.00),
                        LocalDateTime.of(2021, 11, 12, 18, 37, 0),
                        true,
                        LocalDateTime.of(2021, 11, 18, 14, 0, 0)
                ),
                new Product(
                        2L,
                        new Brand(2L, "Apple"),
                        new Model(6L, "11"),
                        new Color(5L, "Rose Gold"),
                        new Country(4L, "France"),
                        20,
                        BigDecimal.valueOf(150.00)
                ))).get().getClientCount());
    }


    public void testTestFindAll() {
        OrderProductFilter filter = new OrderProductFilter(3, 0, null, null, 6);
        assertEquals(3, orderProductDao.findAll(filter).size());
    }


}