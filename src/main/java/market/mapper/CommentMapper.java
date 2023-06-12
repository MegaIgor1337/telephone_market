package market.mapper;

import market.dto.CommentDto;
import market.entity.Comment;
import org.mapstruct.Mapper;

import static market.util.StringContainer.SPRING;


@Mapper(componentModel = SPRING, uses = UserMapper.class)
public interface CommentMapper {
    CommentDto commentToCommentDto(Comment comment);
    Comment commentDtoToComment(CommentDto commentDto);
}
