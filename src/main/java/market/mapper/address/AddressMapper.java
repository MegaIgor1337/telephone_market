package market.mapper.address;

import market.dto.AddressDto;
import market.entity.Address;
import lombok.RequiredArgsConstructor;
import market.mapper.Mapper;
import market.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMapper implements Mapper<Address, AddressDto> {


    private final UserMapper userMapper;

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
