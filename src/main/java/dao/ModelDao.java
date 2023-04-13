package dao;

import entity.brand.Brand;
import exception.DaoException;
import filter.ModelFilter;
import entity.model.Model;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelDao implements Dao<Long, Model, ModelFilter> {

    private static final ModelDao INSTANCE = new ModelDao();

    private static String FIND_BY_ID_SQL = """
            select id, model
            from model
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, model
            from model
            """;

    private static String UPDATE_SQL = """
            update model
            set model = ?
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from model
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into model(model) 
            values (?)
            """;

    private Model buildModel(ResultSet result) throws SQLException {
        return new Model(
                result.getLong("id"),
                result.getString("model")
        );
    }

    @Override
    public boolean update(Model model) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, model.getModel());
            statement.setLong(2, model.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Model> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Model model = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                model = buildModel(result);
            return Optional.ofNullable(model);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Model> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Model> models = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                models.add(buildModel(result));
            return models;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Model> findAll(ModelFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.model() != null) {
            whereSql.add("model like ?");
            parameters.add("%" + filter.model() + "%");
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
            List<Model> models = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                models.add(buildModel(result));
            return models;
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
    public Model save(Model model) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getModel());


            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                model.setId(generatedKeys.getLong("id"));
            return model;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ModelDao() {

    }

    public static ModelDao getInstance() {
        return INSTANCE;
    }
}
