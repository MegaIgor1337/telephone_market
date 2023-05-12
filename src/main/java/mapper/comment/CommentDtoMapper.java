package mapper.comment;

import dto.CommentDto;
import entity.Comment;
import mapper.Mapper;
import mapper.user.UserDtoMapper;
import org.hibernate.Session;

public class CommentDtoMapper implements Mapper<CommentDto, Comment> {
    private static final CommentDtoMapper INSTANCE = new CommentDtoMapper();
    private final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();
    @Override
    public Comment mapFrom(CommentDto object) {
        return Comment.builder()
                .id(object.getId())
                .comment(object.getComment())
                .user(userDtoMapper.mapFrom(object.getUserDto()))
                .status(object.getCommentStatus())
                .build();
    }

    public static CommentDtoMapper getInstance() {
        return INSTANCE;
    }
}
