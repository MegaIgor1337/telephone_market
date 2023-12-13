package market.service.mapper;

import market.service.dto.UserDto;
import market.model.entity.User;
import org.mapstruct.Mapper;

import static market.service.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtotoUser(UserDto userDto);
}