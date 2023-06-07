package market.mapper.user;

import market.dto.UserDto;
import market.entity.User;
import market.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper implements Mapper<UserDto, User> {
    @Override
    public User mapFrom(UserDto object) {
        return User.builder()
                .id(object.getId())
                .name(object.getName())
                .password(object.getPassword())
                .passportNo(object.getPassportNo())
                .email(object.getEmail())
                .role(object.getRole())
                .gender(object.getGender())
                .build();
    }

}
