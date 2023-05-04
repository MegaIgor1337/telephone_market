package service;

import dao.CommentDao;
import dto.comment.CommentDto;
import entity.comment.Comment;
import entity.comment.CommentStatus;
import entity.user.User;
import mapper.comment.CommentDtoMapper;
import mapper.comment.CommentMapper;
import mapper.user.UserDtoMapper;
import mapper.user.UserMapper;

import java.util.List;

public class CommentService {
    private static final CommentService INSTANCE = new CommentService();
    private final CommentDao commentDao = CommentDao.getINSTANCE();
    private final CommentMapper commentMapper = CommentMapper.getInstance();
    private final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();


    private final CommentDtoMapper commentDtoMapper = CommentDtoMapper.getInstance();

    public List<CommentDto> getAccessedComments() {
        return commentDao.get().stream()
                .filter(it -> it.getStatus().equals(CommentStatus.ACCESSED))
                .map(commentMapper::mapFrom).toList();
    }

    public CommentDto save(CommentDto commentDto) {
        return commentMapper.mapFrom(
                commentDao.find(
                        commentDao.save(
                                commentDtoMapper.mapFrom(commentDto)
                        )
                ).get()
        );
    }

    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentDao.findByUserId(id).stream().map(commentMapper::mapFrom).toList();
    }

    public List<CommentDto> getModerateComments() {
        return commentDao.get().stream().filter(it -> it.getStatus().equals(CommentStatus.MODERATING))
                .map(commentMapper::mapFrom).toList();
    }

    public void updateCommentFromModeratingToAccess(Long id) {
        Comment comment = commentDao.find(id).get();
        comment.setStatus(CommentStatus.ACCESSED);
        commentDao.update(comment);
    }

    public void updateCommentFromModeratingToDelete(Long id) {
        Comment comment = commentDao.find(id).get();
        comment.setStatus(CommentStatus.DELETED);
        commentDao.update(comment);
    }

    public void deleteComment(Long id) {
        commentDao.delete(id);
    }

    public static CommentService getInstance() {
        return INSTANCE;
    }
}
