package project.service;

import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.dto.CommentDto;
import market.enums.CommentStatus;
import market.mapper.CommentMapper;
import market.repository.CommentRepository;
import market.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;
import project.annotation.IT;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@IT
@RequiredArgsConstructor
public class CommentServiceTest {
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Test
    void getAccessedComments() {
        var result = commentService.getAccessedComments();
        assertThat(result).hasSize(2);
    }

    @Test
    void save() {
        var comment = commentRepository.findById(1L);
        CommentDto commentDto = commentMapper.commentToCommentDto(comment.get());
        commentDto.setComment("wowowow");
        commentDto.setId(null);
        var result = commentService.save(commentDto.getComment(), 1L);
        assertThat(result.getId()).isEqualTo(5);
    }

    @Test
    void findCommentsByUserId() {
        var result = commentService.findCommentsByUserId(2L);
        assertThat(result).hasSize(2);
    }

    @Test
    void getModerateComments() {
        var result = commentService.getModerateComments();
        assertThat(result).hasSize(2);
    }


    @Test
    void updateCommentFromModeratingToAccess() {
        commentService.updateCommentFromModeratingToAccess(3L);
        commentRepository.findById(3L)
                .ifPresent(c -> assertThat(c.getStatus()).isEqualTo(CommentStatus.ACCESSED));
    }


    @Test
    void deleteComment() {
        commentService.deleteComment(4L);
        assertThat(commentRepository.findAll()).hasSize(3);
    }

}