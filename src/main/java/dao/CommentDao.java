package dao;

import entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


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
