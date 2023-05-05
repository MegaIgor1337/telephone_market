package mapper.comment;

import dao.UserDao;
import dto.CreateCommentDto;
import entity.Comment;
import mapper.Mapper;

public class CreateCommentMapper implements Mapper<CreateCommentDto, Comment> {
    public static final CreateCommentMapper INSTANCE = new CreateCommentMapper();
    private final UserDao userDao = UserDao.getINSTANCE();
    @Override
    public Comment mapFrom(CreateCommentDto object) {
        return Comment.builder()
                .id(Long.valueOf(object.getId()))
                .comment(object.getComment())
                .user(userDao.find(Long.valueOf(object.getUserId())).get())
                .build();
    }

    public static CreateCommentMapper getInstance() {
        return INSTANCE;
    }
}
