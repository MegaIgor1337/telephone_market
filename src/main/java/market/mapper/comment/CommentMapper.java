package market.mapper.comment;

import market.dto.CommentDto;
import market.entity.Comment;
import lombok.RequiredArgsConstructor;
import market.mapper.Mapper;
import market.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper implements Mapper<Comment, CommentDto> {
    private final UserMapper userMapper;
    @Override
    public CommentDto mapFrom(Comment object) {
        return CommentDto.builder()
                .id(object.getId())
                .comment(object.getComment())
                .userDto(userMapper.mapFrom(object.getUser()))
                .commentStatus(object.getStatus())
                .build();
    }


}
