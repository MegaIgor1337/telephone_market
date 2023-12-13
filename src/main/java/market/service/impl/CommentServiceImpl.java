package market.service.impl;


import lombok.RequiredArgsConstructor;
import market.service.dto.CommentDto;
import market.model.entity.Comment;
import market.model.enums.CommentStatus;
import market.service.mapper.CommentMapper;
import market.model.repository.CommentRepository;
import market.model.repository.UserRepository;
import market.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @Override
    public List<CommentDto> getAccessedComments() {
        return commentRepository.getAccessedComments()
                .stream().map(commentMapper::commentToCommentDto).toList();
    }

    @Override
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


    @Override
    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentRepository.findByUserId(id).stream()
                .map(commentMapper::commentToCommentDto).collect(toList());
    }

    @Override
    public List<CommentDto> getModerateComments() {
        return commentRepository.findAll().stream().filter(it -> it.getStatus().equals(CommentStatus.MODERATING))
                .map(commentMapper::commentToCommentDto).collect(toList());
    }

    @Override
    @Transactional
    public void updateCommentFromModeratingToAccess(Long id) {
        var comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setStatus(CommentStatus.ACCESSED);
            commentRepository.saveAndFlush(comment);
        }
    }

    @Override
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
