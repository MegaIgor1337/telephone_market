package dao;

import entity.model.Model;
import entity.order.Order;
import entity.orderproduct.OrderProduct;
import exception.DaoException;
import filter.OrderFilter;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderDao implements Dao<Long, Order, OrderFilter> {
    private static final OrderDao INSTANCE = new OrderDao();
    private static final ClientDao clientDao = ClientDao.getInstance();
    private static String FIND_BY_ID_SQL = """
            select id, client_id, cost, date, delivered, date_of_delivery
            from "order"
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, client_id, cost, date, delivered, date_of_delivery
            from "order"
            """;

    private static String UPDATE_SQL = """
            update "order" 
            set client_id  = ?,
                "cost" = ?,
                "date" = ?,
                delivered = ?,
                date_of_delivery = ?
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from "order"
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into "order"(client_id, cost, date, delivered, date_of_delivery) 
            values (?, ?, ?, ?, ?)
            """;

    private Order buildOrder(ResultSet result) throws SQLException {
        return new Order(
                result.getLong("id"),
                clientDao.findById(result.getLong("client_id")).orElse(null),
                result.getBigDecimal("cost"),
                result.getTimestamp("date").toLocalDateTime(),
                result.getBoolean("delivered"),
                result.getTimestamp("date_of_delivery").toLocalDateTime()
        );
    }

    @Override
    public boolean update(Order order) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, order.getClient().getId());
            statement.setBigDecimal(2, order.getCost());
            statement.setTimestamp(3, Timestamp.valueOf(order.getDate()));
            statement.setBoolean(4, order.getDelivered());
            statement.setTimestamp(5, Timestamp.valueOf(order.getDateOfDelivery()));
            statement.setLong(6, order.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Order order = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                order = buildOrder(result);
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Order> orders = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                orders.add(buildOrder(result));
            return orders;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll(OrderFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.client() != null) {
            whereSql.add("client_id = ?");
            parameters.add(filter.client().getId());
        }
        if (filter.cost() != null) {
            whereSql.add("cost = ?");
            parameters.add(filter.cost());
        }
        if (filter.date() != null) {
            whereSql.add("date = ?");
            parameters.add(filter.date());
        }
        if (filter.delivered() != null) {
            whereSql.add("delivered = ?");
            parameters.add(filter.delivered());
        }
        if (filter.dateOfDelivery() != null) {
            whereSql.add("date_of_delivery = ?");
            parameters.add(filter.dateOfDelivery());
        }


        parameters.add(filter.limit());
        parameters.add(filter.offset());

        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : " ",
                " LIMIT ? OFFSET ?"
        ));
        String sql = FIND_ALL_SQL + where;

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            List<Order> orders = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                orders.add(buildOrder(result));
            return orders;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Order save(Order order) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getClient().getId());
            statement.setBigDecimal(2, order.getCost());
            statement.setTimestamp(3, Timestamp.valueOf(order.getDate()));
            statement.setBoolean(4, order.getDelivered());
            statement.setTimestamp(5, Timestamp.valueOf(order.getDateOfDelivery()));
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                order.setId(generatedKeys.getLong("id"));
            return order;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private OrderDao() {

    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
