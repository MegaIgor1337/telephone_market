package dao;

import entity.product.Product;
import exception.DaoException;
import dao.filter.ProductFilter;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductDao implements Dao<Long, Product> {
    private static final ProductDao INSTANCE = new ProductDao();
    private static final BrandDao brandDao = BrandDao.getInstance();
    private static final ModelDao modelDao = ModelDao.getInstance();
    private static final ColorDao colorDao = ColorDao.getInstance();
    private static final CountryDao countryDao = CountryDao.getInstance();

    private static String FIND_BY_ID_SQL = """
            select id, brand_id, model_id, color_id, country_id, count_on_storage, cost
            from product 
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, brand_id, model_id, color_id, country_id, count_on_storage, cost
            from product
            """;

    private static String UPDATE_SQL = """
            update product
            set brand_id = ?,
                model_id = ?,
                color_id = ?,
                country_id = ?,
                count_on_storage = ?,
                cost = ?        
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from product
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into product(brand_id, model_id, color_id, country_id, count_on_storage, cost) 
            values (?, ?, ?, ?, ?, ?)
            """;

    private Product buildProduct(ResultSet result) throws SQLException {
        return new Product(
                result.getLong("id"),
                brandDao.findById(result.getLong("brand_id")).orElse(null),
                modelDao.findById(result.getLong("model_id")).orElse(null),
                colorDao.findById(result.getLong("color_id")).orElse(null),
                countryDao.findById(result.getLong("country_id")).orElse(null),
                result.getInt("count_on_storage"),
                result.getBigDecimal("cost")
        );
    }

    private ProductDao() {

    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Product product) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, product.getBrand().getId());
            statement.setLong(2, product.getModel().getId());
            statement.setLong(3, product.getColor().getId());
            statement.setLong(4, product.getCountry().getId());
            statement.setInt(5, product.getStorageCount());
            statement.setBigDecimal(6, product.getCost());
            statement.setLong(7, product.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Product product = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                product = buildProduct(result);
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Product> products = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                products.add(buildProduct(result));
            return products;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Product> findAll(ProductFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.brand() != null) {
            whereSql.add("brand_id = ");
            parameters.add(filter.brand().getId());
        }
        if (filter.model() != null) {
            whereSql.add("model_id = ?");
            parameters.add(filter.model().getId());
        }
        if (filter.color() != null) {
            whereSql.add("color_id = ?");
            parameters.add(filter.color().getId());
        }
        if (filter.country() != null) {
            whereSql.add("country_id = ?");
            parameters.add(filter.country().getId());
        }
        if (filter.storageCount() != null) {
            whereSql.add("count_on_storage = ?");
            parameters.add(filter.storageCount());
        }
        if (filter.cost() != null) {
            whereSql.add("cost = ?");
            parameters.add(filter.cost());
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
            List<Product> products = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                products.add(buildProduct(result));

            return products;
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
    public Product save(Product product) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, product.getBrand().getId());
            statement.setLong(2, product.getModel().getId());
            statement.setLong(3, product.getColor().getId());
            statement.setLong(4, product.getCountry().getId());
            statement.setInt(5, product.getStorageCount());
            statement.setBigDecimal(6, product.getCost());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                product.setId(generatedKeys.getLong("id"));
            return product;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
