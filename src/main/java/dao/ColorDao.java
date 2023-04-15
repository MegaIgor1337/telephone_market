package dao;

import entity.color.Color;
import exception.DaoException;
import dao.filter.ColorFilter;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ColorDao implements Dao<Long, Color> {
    private static final ColorDao INSTANCE = new ColorDao();
    private static String FIND_BY_ID_SQL = """
            select id, color
            from color
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, color
            from color
            """;

    private static String UPDATE_SQL = """
            update color
            set color = ?
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from color
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into color(color) 
            values (?)
            """;

    private Color buildColor(ResultSet result) throws SQLException {
        return new Color(
                result.getLong("id"),
                result.getString("color")
        );
    }

    @Override
    public boolean update(Color color) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, color.getColor());
            statement.setLong(2, color.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Color> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Color color = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                color = buildColor(result);
            return Optional.ofNullable(color);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Color> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Color> colors = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                colors.add(buildColor(result));
            return colors;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Color> findAll(ColorFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.color() != null) {
            whereSql.add("color like ?");
            parameters.add("%" + filter.color() + "%");
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
            List<Color> colors = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                colors.add(buildColor(result));
            return colors;
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
    public Color save(Color color) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, color.getColor());


            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                color.setId(generatedKeys.getLong("id"));
            return color;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ColorDao() {

    }

    public static ColorDao getInstance() {
        return INSTANCE;
    }
}
