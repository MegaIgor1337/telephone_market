package dao;

import dao.filter.CommentFilter;
import entity.color.Color;
import entity.comment.Comment;
import entity.country.Country;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentDao implements Dao<Long, Comment> {
    private static final CommentDao INSTANCE = new CommentDao();
    private final UserDao userDao = UserDao.getInstance();

    private static String FIND_BY_ID_SQL = """
            select id, comment, user_id
            from comments
            where id = ?
            """;

    private static String FIND_ALL_SQL = """
            select id, comment, user_id
            from comments
            """;

    private static String UPDATE_SQL = """
            update comments
            set comment = ?,
            user_id = ?
            where id = ?
            """;

    private static String DELETE_SQL = """
            delete from comments
            where id = ?
            """;

    private static String SAVE_SQL = """
            insert into comments(comment, user_id) 
            values (?, ?)
            """;

    private static String FIND_BY_USER_ID_SQL = """
            select id, comment, user_id 
            from comments
            where user_id = ?
            """;

    private Comment buildComment(ResultSet result) throws SQLException {
        return new Comment(
                result.getLong("id"),
                result.getString("comment"),
                userDao.findById(result.getLong("user_id")).orElse(null)
        );
    }

    public List<Comment> findByUserId(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_USER_ID_SQL)) {
            List<Comment> comments = new ArrayList<>();
            statement.setLong(1, id);
            var result = statement.executeQuery();
            while (result.next())
                comments.add(buildComment(result));
            return comments;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Comment comment) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, comment.getComment());
            statement.setLong(2, comment.getUser().getId());
            statement.setLong(3, comment.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            Comment comment = null;
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next())
                comment = buildComment(result);
            return Optional.ofNullable(comment);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Comment> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Comment> comments = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                comments.add(buildComment(result));
            return comments;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Comment> findAll(CommentFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.comment() != null) {
            whereSql.add("comment like ?");
            parameters.add("%" + filter.comment() + "%");
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
            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next())
                comments.add(buildComment(result));
            return comments;
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
    public Comment save(Comment comment) {
        try (var connection = ConnectionManager.get();
             var statement = connection
                     .prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, comment.getComment());
            statement.setLong(2, comment.getUser().getId());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                comment.setId(generatedKeys.getLong("id"));
            return comment;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public static CommentDao getInstance() {
        return INSTANCE;
    }
}
