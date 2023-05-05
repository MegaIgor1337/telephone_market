package dao;

import entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    @Getter
    private static final UserDao INSTANCE = new UserDao();



    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
