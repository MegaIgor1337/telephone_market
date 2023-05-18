package dao;

import entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

public class UserRepository extends RepositoryBase<Long, User> {



    public UserRepository(EntityManager entityManager) {
        super(entityManager, User.class);
    }

}
