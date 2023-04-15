package dao;

import entity.country.Country;
import exception.DaoException;
import dao.filter.CountryFilter;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CountryDao implements Dao<Long, Country> {
    private static final CountryDao INSTANCE = new CountryDao();
    private static String FIND_BY_ID_SQL = """
            select id, name
            from country
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, name
            from country
            """;

    private static String UPDATE_SQL = """
            update country
            set name = ?
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from country
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into country(name) 
            values (?)
            """;

    private Country buildCountry(ResultSet result) throws SQLException {
        return new Country(
                result.getLong("id"),
                result.getString("name")
        );
    }

    @Override
    public boolean update(Country country) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, country.getCountry());
            statement.setLong(2, country.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Country> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Country country = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                country = buildCountry(result);
            return Optional.ofNullable(country);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Country> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Country> countries = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                countries.add(buildCountry(result));
            }
            return countries;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Country> findAll(CountryFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.country() != null) {
            whereSql.add("name like ?");
            parameters.add("%" + filter.country() + "%");
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
            List<Country> countries = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                countries.add(buildCountry(result));
            return countries;
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
    public Country save(Country country) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, country.getCountry());


            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                country.setId(generatedKeys.getLong("id"));
            return country;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private CountryDao() {

    }

    public static CountryDao getInstance() {
        return INSTANCE;
    }
}
