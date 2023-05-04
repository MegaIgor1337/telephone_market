package dao;

import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import exception.DaoException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    @Getter
    private static final UserDao INSTANCE = new UserDao();



    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
