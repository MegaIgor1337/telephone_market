package mapper.address;

import dao.UserDao;
import dto.address.CreateAddressDto;
import dto.user.CreateUserDto;
import dto.user.UserDto;
import entity.address.Address;
import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import lombok.NoArgsConstructor;
import mapper.Mapper;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)

public class CreateAddressMapper implements Mapper<CreateAddressDto, Address> {
    private static final CreateAddressMapper INSTANCE = new CreateAddressMapper();
    private final UserDao userDao = UserDao.getINSTANCE();

    public static CreateAddressMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Address mapFrom(CreateAddressDto object) {

            return Address.builder()
                    .id(Long.valueOf(object.getId()))
                    .country(object.getCountry())
                    .city(object.getCity())
                    .street(object.getStreet())
                    .house(object.getHouse())
                    .flat(object.getFlat())
                    .user(userDao.find(Long.valueOf(object.getUserId())).get())
                    .build();
    }
}
