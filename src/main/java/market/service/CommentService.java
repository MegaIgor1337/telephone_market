package market.service;


import lombok.RequiredArgsConstructor;
import market.dto.CommentDto;
import market.entity.Comment;
import market.enums.CommentStatus;
import market.mapper.CommentMapper;
import market.repository.CommentRepository;
import market.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public List<CommentDto> getAccessedComments() {
        return commentRepository.getAccessedComments()
                .stream().map(commentMapper::commentToCommentDto).toList();
    }


    @Transactional
    public CommentDto save(String commentStr, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Comment comment = Comment.builder()
                .comment(commentStr)
                .status(CommentStatus.MODERATING)
                .user(user)
                .build();
        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }


    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentRepository.findByUserId(id).stream()
                .map(commentMapper::commentToCommentDto).collect(toList());
    }

    public List<CommentDto> getModerateComments() {
        return commentRepository.findAll().stream().filter(it -> it.getStatus().equals(CommentStatus.MODERATING))
                .map(commentMapper::commentToCommentDto).collect(toList());
    }

    @Transactional
    public void updateCommentFromModeratingToAccess(Long id) {
        var comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setStatus(CommentStatus.ACCESSED);
            commentRepository.saveAndFlush(comment);
        }
    }

    @Transactional
    public boolean deleteComment(Long id) {
        return commentRepository.findById(id)
                .map(entity -> {
                    commentRepository.delete(entity);
                    commentRepository.flush();
                    return true;
                })
                .orElse(false);
    }


}
