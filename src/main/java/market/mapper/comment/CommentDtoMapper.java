package market.mapper.comment;

import market.dto.CommentDto;
import market.entity.Comment;
import lombok.RequiredArgsConstructor;
import market.mapper.Mapper;
import market.mapper.user.UserDtoMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDtoMapper implements Mapper<CommentDto, Comment> {
    private final UserDtoMapper userDtoMapper;
    @Override
    public Comment mapFrom(CommentDto object) {
        return Comment.builder()
                .id(object.getId())
                .comment(object.getComment())
                .user(userDtoMapper.mapFrom(object.getUserDto()))
                .status(object.getCommentStatus())
                .build();
    }


}
