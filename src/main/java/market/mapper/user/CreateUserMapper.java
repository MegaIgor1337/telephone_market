package market.mapper.user;

import market.dto.CreateUserDto;
import market.enums.Gender;
import market.enums.Role;
import market.entity.User;
import market.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CreateUserMapper implements Mapper<CreateUserDto, User> {


    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .password(object.getPassword())
                .passportNo(object.getPassportNo())
                .email(object.getEmail())
                .role(Role.valueOf(object.getRole()))
                .gender(Gender.valueOf(object.getGender()))
                .build();
    }

}
