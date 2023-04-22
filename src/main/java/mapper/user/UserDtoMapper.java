package mapper.user;

import dto.user.UserDto;
import entity.user.User;
import mapper.Mapper;

public class UserDtoMapper implements Mapper<UserDto, User> {
    private static final UserDtoMapper INSTANCE = new UserDtoMapper();
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
    public static UserDtoMapper getInstance() {
        return INSTANCE;
    }
}
