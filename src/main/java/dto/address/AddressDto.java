package dto.address;

import dto.user.UserDto;
import entity.user.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddressDto {
    Long id;
    String country;
    String city;
    String street;
    String house;
    String flat;
    UserDto user;
}
