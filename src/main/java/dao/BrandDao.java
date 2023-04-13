package dao;

import filter.BrandFilter;
import entity.brand.Brand;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BrandDao implements Dao<Long, Brand, BrandFilter> {
    private static final BrandDao INSTANCE = new BrandDao();

    private static String FIND_BY_ID_SQL = """
            select id, brand
            from brand
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, brand
            from brand
            """;

    private static String UPDATE_SQL = """
            update brand
            set brand = ?
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from brand
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into brand(brand) 
            values (?)
            """;

    private Brand buildBrand(ResultSet result) throws SQLException {
        return new Brand(
                result.getLong("id"),
                result.getString("brand")
        );
    }

    @Override
    public boolean update(Brand brand) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, brand.getBrand());
            statement.setLong(2, brand.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Brand> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Brand brand = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                brand = buildBrand(result);
            return Optional.ofNullable(brand);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Brand> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Brand> brands = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                brands.add(buildBrand(result));
            return brands;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Brand> findAll(BrandFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.brand() != null) {
            whereSql.add("brand like ?");
            parameters.add("%" + filter.brand() + "%");
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
            List<Brand> brands = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                brands.add(buildBrand(result));
            return brands;
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
    public Brand save(Brand brand) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, brand.getBrand());


            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                brand.setId(generatedKeys.getLong("id"));
            return brand;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private BrandDao() {

    }

    public static BrandDao getInstance() {
        return INSTANCE;
    }
}
