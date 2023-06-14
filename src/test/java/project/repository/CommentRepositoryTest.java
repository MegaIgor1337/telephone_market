package project.repository;


import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.entity.Comment;
import market.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;
import project.annotation.IT;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class CommentRepositoryTest {
    private final CommentRepository commentRepository;
    @Test
    public void testFindByUserId() {
        List<Comment> comments = commentRepository.findByUserId(2L);
        assertThat(2).isEqualTo(comments.size());
    }


}
