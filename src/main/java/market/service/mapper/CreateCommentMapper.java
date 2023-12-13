package market.service.mapper;

import market.service.dto.CreateCommentDto;
import market.model.entity.Comment;
import market.model.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static market.service.util.ConstantContainer.*;


@Mapper(componentModel = SPRING)
public abstract class CreateCommentMapper {
    @Autowired
    protected UserRepository userRepository;


    @Mapping(target = USER, expression = EXPRESSION_CREATE_COMMENT)
    public abstract Comment CreateCommentDtoToComment(CreateCommentDto createCommentDto);

}