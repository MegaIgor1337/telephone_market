package market.mapper.user;

import market.dto.UserDto;
import market.entity.User;
import market.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDto> {


    @Override
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .id(object.getId())
                .name(object.getName())
                .password(object.getPassword())
                .passportNo(object.getPassportNo())
                .email(object.getEmail())
                .role(object.getRole())
                .gender(object.getGender())
                .balance(object.getBalance())
                .build();
    }

}