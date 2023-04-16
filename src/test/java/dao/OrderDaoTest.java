package dao;

import entity.address.Address;
import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import entity.order.Order;
import dao.filter.OrderFilter;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDaoTest extends TestCase {
    private static final OrderDao orderDao = OrderDao.getInstance();

    public void testUpdate() {
        Order order = new Order(
                3L,
                new User(
                        3L,
                        "Gennadiy22",
                        "boring",
                        "HH333222",
                        "goodjob@gmail.com",
                        Role.USER,
                        Gender.MALE
                ),
                BigDecimal.valueOf(215.00),
                LocalDateTime.of(2021, 11, 12, 18, 37, 0),
                true,
                LocalDateTime.of(2021, 11, 18, 14, 0, 0)
        );
        assertTrue(orderDao.update(order));
    }

    public void testFindById() {
        if (orderDao.findById(2L).isPresent()) {
            assertTrue(orderDao.findById(2L).get().getDelivered());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        OrderFilter filter = new OrderFilter(4, 0, null,
                null, null, true, null);
        assertEquals(4, orderDao.findAll(filter).size());
    }



}