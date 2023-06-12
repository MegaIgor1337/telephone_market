package market.mapper;

import market.dto.CreateUserDto;
import market.entity.User;
import org.mapstruct.Mapper;

import static market.util.StringContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface CreateUserMapper  {
    User createUserDtoToUser(CreateUserDto createUserDto);
}
