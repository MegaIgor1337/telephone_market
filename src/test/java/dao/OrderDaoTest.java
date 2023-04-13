package dao;

import entity.address.Address;
import entity.client.Client;
import entity.order.Order;
import filter.OrderFilter;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDaoTest extends TestCase {
    private static final OrderDao orderDao = OrderDao.getInstance();

    public void testUpdate() {
        Order order = new Order(
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

    public void testSave() {
        Order order = new Order(
                null,
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
                BigDecimal.valueOf(285.00),
                LocalDateTime.of(2021, 10, 12, 18, 37, 0),
                true,
                LocalDateTime.of(2021, 12, 18, 14, 0, 0)
        );
        orderDao.save(order);
        assertEquals(orderDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(orderDao.findAll().size() - 1).getId(), order.getId(), order.getId());
    }

    public void testDelete() {
        assertTrue(orderDao.delete(orderDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(orderDao.findAll().size() - 1).getId()));
    }


}