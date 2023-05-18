package dao;

import entity.Comment;
import org.hibernate.SessionFactory;

import java.util.List;

public interface CommentDao {
    List<Comment> findByUserId(Long id);
    List<Comment> getAccessedComments();
}
