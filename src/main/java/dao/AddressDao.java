package dao;

import dao.filter.AddressFilter;
import entity.address.Address;
import entity.user.User;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddressDao implements Dao<Long, Address> {
    private static final AddressDao INSTANCE = new AddressDao();
    private final UserDao userDao = UserDao.getInstance();

    private static String FIND_BY_ID_SQL = """
            select id, country, city, street, house, flat, user_id
            from address
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, country, city, street, house, flat, user_id  
            from address
            """;

    private static String UPDATE_SQL = """
            update address
            set country = ?,
            city = ?,
            street = ?,
            house = ?,
            flat =?,
            user_id = ?
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from address
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into address(country, city, street, house, flat, user_id) 
            values (?, ?, ?, ?, ?, ?)
            """;

    private static String FIND_BY_USER_ID = """
            select id, country, street, house, flat, city, user_id from address
            where user_id = ?
            """;

    private Address buildAddress(ResultSet result) throws SQLException {
        return new Address(
                result.getLong("id"),
                result.getString("country"),
                result.getString("city"),
                result.getString("street"),
                result.getString("house"),
                result.getString("flat"),
                userDao.findById(result.getLong("user_id")).orElse(null)
        );
    }

    @Override
    public boolean update(Address address) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getStreet());
            statement.setString(4, address.getHouse());
            statement.setString(5, address.getFlat());
            statement.setLong(6, address.getUser().getId());
            statement.setLong(7, address.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Address> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Address address = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                address = buildAddress(result);
            return Optional.ofNullable(address);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Address> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Address> addresses = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                addresses.add(buildAddress(result));
            return addresses;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Address> findAll(AddressFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.country() != null) {
            whereSql.add("country like ?");
            parameters.add("%" + filter.country() + "%");
        }
        if (filter.city() != null) {
            whereSql.add("city = ?");
            parameters.add(filter.city());
        }
        if (filter.street() != null) {
            whereSql.add("street = ?");
            parameters.add(filter.street());
        }
        if (filter.house() != null) {
            whereSql.add("house = ?");
            parameters.add(filter.house());
        }
        if (filter.flat() != null) {
            whereSql.add("flat = ?");
            parameters.add(filter.flat());
        }
        if (filter.user() != null) {
            whereSql.add("user_id = ?");
            parameters.add(filter.user().getId());
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
            List<Address> addresses = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                addresses.add(buildAddress(result));

            return addresses;
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
    public Address save(Address address) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getCity());
            statement.setString(3, address.getStreet());
            statement.setString(4, address.getHouse());
            statement.setString(5, address.getFlat());
            statement.setLong(6, address.getUser().getId());

            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                address.setId(generatedKeys.getLong("id"));
            return address;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Address> getAddressesByUserId(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USER_ID)) {
            List<Address> addresses = new ArrayList<>();
            statement.setLong(1, id);
            var result = statement.executeQuery();
            while (result.next())
                addresses.add(buildAddress(result));
            return addresses;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static AddressDao getInstance() {
        return INSTANCE;
    }

    private AddressDao() {

    }
}
