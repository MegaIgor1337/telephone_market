package market.mapper;

import market.dto.CreateCommentDto;
import market.entity.Comment;
import market.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static market.util.ConstantContainer.*;


@Mapper(componentModel = SPRING)
public abstract class CreateCommentMapper {
    @Autowired
    protected UserRepository userRepository;


    @Mapping(target = USER, expression = EXPRESSION_CREATE_COMMENT)
    public abstract Comment CreateCommentDtoToComment(CreateCommentDto createCommentDto);

}