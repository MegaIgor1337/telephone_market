package mapper.comment;

import dao.UserDao;
import dto.comment.CreateCommentDto;
import entity.comment.Comment;
import mapper.Mapper;
import mapper.user.UserMapper;

public class CreateCommentMapper implements Mapper<CreateCommentDto, Comment> {
    public static final CreateCommentMapper INSTANCE = new CreateCommentMapper();
    private final UserDao userDao = UserDao.getInstance();
    @Override
    public Comment mapFrom(CreateCommentDto object) {
        return Comment.builder()
                .id(Long.valueOf(object.getId()))
                .comment(object.getComment())
                .user(userDao.findById(Long.valueOf(object.getUserId())).get())
                .build();
    }

    public static CreateCommentMapper getInstance() {
        return INSTANCE;
    }
}
