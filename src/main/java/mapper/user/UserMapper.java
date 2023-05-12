package mapper.user;

import dto.UserDto;
import entity.User;
import lombok.NoArgsConstructor;
import mapper.Mapper;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<User, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

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

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}