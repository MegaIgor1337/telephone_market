package service;

import dao.CommentDao;
import dto.CommentDto;
import entity.Comment;
import entity.enums.CommentStatus;
import mapper.comment.CommentDtoMapper;
import mapper.comment.CommentMapper;
import mapper.user.UserDtoMapper;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;

public class CommentService {
    private static final CommentService INSTANCE = new CommentService();
    private final CommentDao commentDao = CommentDao.getINSTANCE();
    private final CommentMapper commentMapper = CommentMapper.getInstance();
    private final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    private final CommentDtoMapper commentDtoMapper = CommentDtoMapper.getInstance();

    public List<CommentDto> getAccessedComments() {
        return commentDao.get(sessionFactory).stream()
                .filter(it -> it.getStatus().equals(CommentStatus.ACCESSED))
                .map(commentMapper::mapFrom).toList();
    }

    public CommentDto save(CommentDto commentDto) {
        return commentMapper.mapFrom(
                commentDao.find(
                        sessionFactory,
                        commentDao.save(
                                sessionFactory,
                                commentDtoMapper.mapFrom(commentDto)
                        )
                ).get()
        );
    }

    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentDao.findByUserId(sessionFactory, id).stream().map(commentMapper::mapFrom).toList();
    }

    public List<CommentDto> getModerateComments() {
        return commentDao.get(sessionFactory).stream().filter(it -> it.getStatus().equals(CommentStatus.MODERATING))
                .map(commentMapper::mapFrom).toList();
    }

    public void updateCommentFromModeratingToAccess(Long id) {
        Comment comment = commentDao.find(sessionFactory, id).get();
        comment.setStatus(CommentStatus.ACCESSED);
        commentDao.update(sessionFactory, comment);
    }

    public void updateCommentFromModeratingToDelete(Long id) {
        Comment comment = commentDao.find(sessionFactory, id).get();
        comment.setStatus(CommentStatus.DELETED);
        commentDao.update(sessionFactory, comment);
    }

    public void deleteComment(Long id) {
        commentDao.delete(id, sessionFactory);
    }

    public static CommentService getInstance() {
        return INSTANCE;
    }
}
