package mapper.comment;

import dto.comment.CommentDto;
import entity.comment.Comment;
import mapper.Mapper;
import mapper.user.UserMapper;

public class CommentMapper implements Mapper<Comment, CommentDto> {
    private final static CommentMapper INSTANCE = new CommentMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public CommentDto mapFrom(Comment object) {
        return CommentDto.builder()
                .id(object.getId())
                .comment(object.getComment())
                .userDto(userMapper.mapFrom(object.getUser()))
                .build();
    }

    public static CommentMapper getInstance() {
        return INSTANCE;
    }
}
