package market.model.repository;

import market.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByUserId(Long id);
    @Query("select c from Comment c where c.status = 'ACCESSED'")
    List<Comment> getAccessedComments();


}
