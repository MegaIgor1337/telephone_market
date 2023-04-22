package dto.address;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
public class CreateAddressDto {
    String id;
    String country;
    String city;
    String street;
    String house;
    String flat;
    String userId;

}
