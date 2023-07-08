package market.mapper;

import market.dto.UserDto;
import market.entity.User;
import org.mapstruct.Mapper;

import static market.util.ConstantContainer.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtotoUser(UserDto userDto);
}