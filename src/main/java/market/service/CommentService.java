package market.service;


import market.repository.CommentRepository;
import market.dto.CommentDto;
import market.entity.Comment;
import market.enums.CommentStatus;
import lombok.RequiredArgsConstructor;
import market.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;


    public List<CommentDto> getAccessedComments() {
        return commentRepository.getAccessedComments()
                .stream().map(commentMapper::commentToCommentDto).collect(Collectors.toList());
    }


    @Transactional
    public CommentDto save(CommentDto commentDto) {
        return commentMapper.commentToCommentDto(commentRepository
                .save(commentMapper.commentDtoToComment(commentDto)));
    }


    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentRepository.findByUserId(id).stream()
                .map(commentMapper::commentToCommentDto).collect(Collectors.toList());
    }

    public List<CommentDto> getModerateComments() {
        return commentRepository.findAll().stream().filter(it -> it.getStatus().equals(CommentStatus.MODERATING))
                .map(commentMapper::commentToCommentDto).collect(Collectors.toList());
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
    public void deleteComment(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.delete(commentRepository.findById(id).get());
        }
    }


}
