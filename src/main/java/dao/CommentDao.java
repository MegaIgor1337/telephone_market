package dao;

import entity.address.Address;
import entity.color.Color;
import entity.comment.Comment;
import entity.country.Country;
import exception.DaoException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentDao implements Dao<Long, Comment> {
    @Getter
    private static final CommentDao INSTANCE = new CommentDao();

    public List<Comment> findByUserId(Long id) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Comment> eList = session.createQuery("FROM Comment where user.id = " + id + " ",
                    getEntityClass()).list();
            session.getTransaction().commit();
            return eList;
        }
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
