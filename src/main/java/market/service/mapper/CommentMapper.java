package market.service.mapper;

import market.service.dto.CommentDto;
import market.model.entity.Comment;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;


@Mapper(componentModel = SPRING, uses = UserMapper.class)
public interface CommentMapper {
    CommentDto commentToCommentDto(Comment comment);
    Comment commentDtoToComment(CommentDto commentDto);
}
