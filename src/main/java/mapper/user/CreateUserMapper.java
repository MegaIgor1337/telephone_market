package mapper.user;

import dao.AddressDao;
import dto.user.CreateUserDto;
import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import lombok.NoArgsConstructor;
import mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

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

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
