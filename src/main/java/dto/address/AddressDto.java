package dto.address;

import dto.user.UserDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
    private UserDto userDto;
}
