package mapper.address;

import dto.AddressDto;
import entity.Address;
import lombok.NoArgsConstructor;
import mapper.Mapper;
import mapper.user.UserMapper;
import org.hibernate.Session;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)

public class AddressMapper implements Mapper<Address, AddressDto> {
    private static final AddressMapper INSTANCE = new AddressMapper();
    private final UserMapper userMapper = UserMapper.getInstance();
    public static AddressMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public AddressDto mapFrom( Address object) {
        return AddressDto.builder()
                .id(object.getId())
                .country(object.getCountry())
                .city(object.getCity())
                .street(object.getStreet())
                .house(object.getHouse())
                .flat(object.getFlat())
                .userDto(userMapper.mapFrom(object.getUser()))
                .build();
    }
}
