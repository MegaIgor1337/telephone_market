package dao;

import entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao implements Dao<Long, Order> {
    @Getter
    private static final OrderDao INSTANCE = new OrderDao();

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }
}
