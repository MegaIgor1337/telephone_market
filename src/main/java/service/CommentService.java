package service;

import dao.CommentRepository;
import dto.CommentDto;
import entity.Comment;
import entity.enums.CommentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mapper.comment.CommentDtoMapper;
import mapper.comment.CommentMapper;
import mapper.user.UserDtoMapper;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentService {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Getter
    private static final CommentService INSTANCE = new CommentService();
    private final CommentMapper commentMapper = CommentMapper.getInstance();
    private final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();
    private final CommentRepository commentRepository = new CommentRepository(HibernateUtil
            .getSessionFromFactory(sessionFactory));
    private final CommentDtoMapper commentDtoMapper = CommentDtoMapper.getInstance();


    public List<CommentDto> getAccessedComments() {
        return commentRepository.getAccessedComments()
                .stream().map(commentMapper::mapFrom).toList();
    }


    public CommentDto save(CommentDto commentDto) {
        return commentMapper.mapFrom(commentRepository.save(commentDtoMapper.mapFrom(commentDto)));
    }

    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentRepository.findByUserId(id).stream().map(commentMapper::mapFrom).toList();
    }

    public List<CommentDto> getModerateComments() {
        return commentRepository.findAll().stream().filter(it -> it.getStatus().equals(CommentStatus.MODERATING))
                .map(commentMapper::mapFrom).toList();
    }

    public void updateCommentFromModeratingToAccess(Long id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setStatus(CommentStatus.ACCESSED);
        commentRepository.update(comment);
    }

    public void updateCommentFromModeratingToDelete(Long id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setStatus(CommentStatus.DELETED);
        commentRepository.update(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }


}
