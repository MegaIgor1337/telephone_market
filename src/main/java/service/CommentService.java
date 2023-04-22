package service;

import dao.CommentDao;
import dto.comment.CommentDto;
import entity.comment.Comment;
import entity.user.User;
import mapper.comment.CommentDtoMapper;
import mapper.comment.CommentMapper;
import mapper.user.UserDtoMapper;
import mapper.user.UserMapper;

import java.util.List;

public class CommentService {
    private static final CommentService INSTANCE = new CommentService();
    private final CommentDao commentDao = CommentDao.getInstance();
    private final CommentMapper commentMapper = CommentMapper.getInstance();
    private final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();

    private final CommentDtoMapper commentDtoMapper = CommentDtoMapper.getInstance();
    public List<CommentDto> get() {
        return commentDao.findAll().stream().map(commentMapper::mapFrom).toList();
    }

    public CommentDto save(CommentDto commentDto) {
        return commentMapper.mapFrom(
                commentDao.save(
                        new Comment(
                                null,
                                commentDto.getComment(),
                                userDtoMapper.mapFrom(commentDto.getUserDto())
                        )
                )
        );
    }

    public List<CommentDto> findCommentsByUserId(Long id) {
        return commentDao.findByUserId(id).stream().map(commentDtoMapper::mapFrom).toList();
    }

    public void deleteComment(Long id) {
        commentDao.delete(id);
    }
    public static CommentService getInstance() {
        return INSTANCE;
    }
}
