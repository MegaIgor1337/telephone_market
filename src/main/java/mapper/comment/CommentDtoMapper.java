package mapper.comment;

import dto.comment.CommentDto;
import entity.comment.Comment;
import mapper.Mapper;
import mapper.user.UserDtoMapper;
import mapper.user.UserMapper;

public class CommentDtoMapper implements Mapper<Comment, CommentDto> {
    private static final CommentDtoMapper INSTANCE = new CommentDtoMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public CommentDto mapFrom(Comment object) {
        return CommentDto.builder()
                .id(object.getId())
                .comment(object.getComment())
                .userDto(userMapper.mapFrom(object.getUser()))
                .build();
    }

    public static CommentDtoMapper getInstance() {
        return INSTANCE;
    }
}
