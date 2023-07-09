package project.repository;


import lombok.RequiredArgsConstructor;
import market.entity.Comment;
import market.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RequiredArgsConstructor
@IT
public class CommentRepositoryTest  {
    private final CommentRepository commentRepository;
    @Test
    public void testFindByUserId() {
        List<Comment> comments = commentRepository.findByUserId(2L);
        assertThat(2).isEqualTo(comments.size());
    }


}
