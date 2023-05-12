package mapper.comment;

import dto.CommentDto;
import entity.Comment;
import mapper.Mapper;
import mapper.user.UserMapper;
import org.hibernate.Session;

public class CommentMapper implements Mapper<Comment, CommentDto> {
    private final static CommentMapper INSTANCE = new CommentMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public CommentDto mapFrom(Comment object) {
        return CommentDto.builder()
                .id(object.getId())
                .comment(object.getComment())
                .userDto(userMapper.mapFrom(object.getUser()))
                .commentStatus(object.getStatus())
                .build();
    }

    public static CommentMapper getInstance() {
        return INSTANCE;
    }
}
