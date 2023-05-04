package dao;

import entity.order.Order;
import exception.DaoException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao implements Dao<Long, Order> {
    @Getter
    private static final OrderDao INSTANCE = new OrderDao();

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }
}
