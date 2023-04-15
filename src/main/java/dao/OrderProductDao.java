package dao;

import entity.orderproduct.OrderProduct;
import entity.orderproduct.PrimaryKeyOrderProduct;
import exception.DaoException;
import dao.filter.OrderProductFilter;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderProductDao implements Dao<PrimaryKeyOrderProduct, OrderProduct> {
    private static final OrderProductDao INSTANCE = new OrderProductDao();
    private static final OrderDao orderDao = OrderDao.getInstance();
    private static final ProductDao productDao = ProductDao.getInstance();

    private static String FIND_BY_ID_SQL = """
            select order_id, product_id, user_quantity
            from order_product 
            where order_id = ? and product_id = ?
            """;

    private static String FIND_ALL_SQL = """
            select order_id, product_id, user_quantity 
            from order_product
            """;

    private static String UPDATE_SQL = """
            update order_product
            set user_quantity = ?
            where order_id = ? and product_id = ?
            """;

    private static String DELETE_SQL = """
            delete from order_product           
            where order_id = ? and product_id = ?
            """;

    private static String SAVE_SQL = """
            insert into order_product(order_id, product_id, user_quantity) 
            values (?, ?, ?)
            """;

    private OrderProduct buildOrderProduct(ResultSet result) throws SQLException {
        return new OrderProduct(
                new PrimaryKeyOrderProduct(
                        orderDao.findById(result.getLong("order_id")).orElse(null),
                        productDao.findById(result.getLong("product_id")).orElse(null)),
                result.getInt("user_quantity")
        );
    }

    @Override
    public boolean update(OrderProduct orderProduct) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, orderProduct.getClientCount());
            statement.setLong(2, orderProduct.getPrimaryKeyOrderProduct().getOrder().getId());
            statement.setLong(3, orderProduct.getPrimaryKeyOrderProduct().getProduct().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<OrderProduct> findById(PrimaryKeyOrderProduct primaryKeyOrderProduct) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            OrderProduct orderProduct = null;
            statement.setLong(1, primaryKeyOrderProduct.getOrder().getId());
            statement.setLong(2, primaryKeyOrderProduct.getProduct().getId());
            var result = statement.executeQuery();
            if (result.next())
                orderProduct = buildOrderProduct(result);
            return Optional.ofNullable(orderProduct);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderProduct> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<OrderProduct> orderProducts = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                orderProducts.add(buildOrderProduct(result));
            return orderProducts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<OrderProduct> findAll(OrderProductFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.order() != null) {
            whereSql.add("brand_id = ");
            parameters.add(filter.order().getId());
        }
        if (filter.product() != null) {
            whereSql.add("model_id = ?");
            parameters.add(filter.product().getId());
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
            List<OrderProduct> orderProducts = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                orderProducts.add(buildOrderProduct(result));

            return orderProducts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(PrimaryKeyOrderProduct primaryKeyOrderProduct) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, primaryKeyOrderProduct.getOrder().getId());
            statement.setLong(2, primaryKeyOrderProduct.getProduct().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL)) {
            statement.setLong(1, orderProduct.getPrimaryKeyOrderProduct().getOrder().getId());
            statement.setLong(2, orderProduct.getPrimaryKeyOrderProduct().getProduct().getId());
            statement.setLong(3, orderProduct.getClientCount());
            statement.executeUpdate();
            return orderProduct;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private OrderProductDao() {

    }

    public static OrderProductDao getInstance() {
        return INSTANCE;
    }
}
