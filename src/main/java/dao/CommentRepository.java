package dao;

import entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;


public class CommentRepository extends RepositoryBase<Long, Comment> implements CommentDao {
    EntityManager entityManager;

    public CommentRepository(EntityManager entityManager) {
        super(entityManager, Comment.class);
        this.entityManager = entityManager;
    }

    public List<Comment> findByUserId(Long id) {
        entityManager.getTransaction().begin();
        List<Comment> comments = entityManager.createQuery("FROM Comment where user.id = :id ",
                        Comment.class)
                .setParameter("id", id)
                .getResultList();
        entityManager.getTransaction().commit();
        return comments;

    }

    public List<Comment> getAccessedComments() {
        entityManager.getTransaction().begin();
        List<Comment> comments = entityManager.createQuery("from Comment c where c.status = 'ACCESSED'",
                Comment.class).getResultList();

        entityManager.getTransaction().commit();
        return comments;
    }


}
