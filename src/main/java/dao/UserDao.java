package dao;

import entity.address.Address;
import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import exception.DaoException;
import dao.filter.UserFilter;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();
    private static final AddressDao addressDao = AddressDao.getInstance();
    private static String FIND_BY_ID_SQL = """
            select id, name, password, passport_no, address_id, email, role, gender
            from users
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, name, password, passport_no, address_id, email, role, gender
            from users
            """;

    private static String UPDATE_SQL = """
            update users
            set name = ?,
                password = ?,
                passport_no = ?,
                address_id = ?,
                email = ?        
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from users
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into users(name, password, passport_no, address_id, email, role, gender) 
            values (?, ?, ?, ?, ?, ?, ?)
            """;

    private User buildClient(ResultSet result) throws SQLException {
        return new User(
                result.getLong("id"),
                result.getString("name"),
                result.getString("password"),
                result.getString("passport_no"),
                addressDao.findById(result.getLong("address_id")).orElse(null),
                result.getString("email"),
                Role.valueOf(result.getString("role")),
                Gender.valueOf(result.getString("gender"))
        );
    }

    @Override
    public boolean update(User client) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getPassword());
            statement.setString(3, client.getPassportNo());
            statement.setLong(4, client.getAddress().getId());
            statement.setString(5, client.getEmail());
            statement.setLong(6, client.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            User client = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                client = buildClient(result);
            return Optional.ofNullable(client);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<User> clients = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                clients.add(buildClient(result));
            return clients;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<User> findAll(UserFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.name() != null) {
            whereSql.add("name like ?");
            parameters.add("%" + filter.name() + "%");
        }
        if (filter.password() != null) {
            whereSql.add("password = ?");
            parameters.add(filter.password());
        }
        if (filter.password() != null) {
            whereSql.add("password = ?");
            parameters.add(filter.password());
        }
        if (filter.address() != null) {
            whereSql.add("address_id = ?");
            parameters.add(filter.address().getId());
        }
        if (filter.email() != null) {
            whereSql.add("email = ?");
            parameters.add(filter.email());
        }
        if (filter.role() != null) {
            whereSql.add("role = ?");
            parameters.add(filter.role());
        }
        if (filter.gender() != null) {
            whereSql.add("gender = ?");
            parameters.add(filter.gender());
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
            List<User> clients = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                clients.add(buildClient(result));

            return clients;
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
    public User save(User client) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            Address address = addressDao.save(client.getAddress());
            statement.setString(1, client.getName());
            statement.setString(2, client.getPassword());
            statement.setString(3, client.getPassportNo());
            statement.setLong(4, address.getId());
            statement.setString(5, client.getEmail());
            statement.setString(6, client.getRole().toString());
            statement.setString(7, client.getGender().toString());

            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                client.setId(generatedKeys.getLong("id"));
            return client;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private UserDao() {

    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}