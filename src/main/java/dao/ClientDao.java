package dao;

import entity.address.Address;
import entity.client.Client;
import exception.DaoException;
import filter.ClientFilter;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientDao implements Dao<Long, Client, ClientFilter> {
    private static final ClientDao INSTANCE = new ClientDao();
    private static final AddressDao addressDao = AddressDao.getInstance();
    private static String FIND_BY_ID_SQL = """
            select id, name, password, passport_no, address_id, email
            from "client"
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, name, password, passport_no, address_id, email
            from "client"
            """;

    private static String UPDATE_SQL = """
            update "client"
            set name = ?,
                password = ?,
                passport_no = ?,
                address_id = ?,
                email = ?        
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from "client"
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into "client"(name, password, passport_no, address_id, email) 
            values (?, ?, ?, ?, ?)
            """;

    private Client buildClient(ResultSet result) throws SQLException {
        return new Client(
                result.getLong("id"),
                result.getString("name"),
                result.getString("password"),
                result.getString("passport_no"),
                addressDao.findById(result.getLong("address_id")).orElse(null),
                result.getString("email")
        );
    }

    @Override
    public boolean update(Client client) {
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
    public Optional<Client> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Client client = null;
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
    public List<Client> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Client> clients = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                clients.add(buildClient(result));
            return clients;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Client> findAll(ClientFilter filter) {
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
            List<Client> clients = new ArrayList<>();
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
    public Client save(Client client) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            Address address = addressDao.save(client.getAddress());
            statement.setString(1, client.getName());
            statement.setString(2, client.getPassword());
            statement.setString(3, client.getPassportNo());
            statement.setLong(4, address.getId());
            statement.setString(5, client.getEmail());


            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                client.setId(generatedKeys.getLong("id"));
            return client;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ClientDao() {

    }

    public static ClientDao getInstance() {
        return INSTANCE;
    }
}
