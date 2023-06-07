package market.service;


import market.repository.CommentRepository;
import market.dto.CommentDto;
import market.entity.Comment;
import market.enums.CommentStatus;
import lombok.RequiredArgsConstructor;
import market.mapper.comment.CommentDtoMapper;
import market.mapper.comment.CommentMapper;
import market.mapper.user.UserDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final UserDtoMapper userDtoMapper;
    private final CommentRepository commentRepository;
    private final CommentDtoMapper commentDtoMapper;


    public List<CommentDto> getAccessedComments() {
        return commentRepository.getAccessedComments()
                .stream().map(commentMapper::mapFrom).collect(Collectors.toList());
    }


    public CommentDto save(CommentDto commentDto) {
        return commentMapper.mapFrom(commentRepository.save(commentDtoMapper.mapFrom(commentDto)));
    }

    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentRepository.findByUserId(id).stream().map(commentMapper::mapFrom).collect(Collectors.toList());
    }

    public List<CommentDto> getModerateComments() {
        return commentRepository.findAll().stream().filter(it -> it.getStatus().equals(CommentStatus.MODERATING))
                .map(commentMapper::mapFrom).collect(Collectors.toList());
    }

    public void updateCommentFromModeratingToAccess(Long id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setStatus(CommentStatus.ACCESSED);
        commentRepository.save(comment);
    }

    public void updateCommentFromModeratingToDelete(Long id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setStatus(CommentStatus.DELETED);
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.delete(commentRepository.findById(id).get());
        }
    }


}
