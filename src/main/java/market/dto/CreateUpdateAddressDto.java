package market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateAddressDto implements ICreateAddressDto {
    private String id;
    private String country;

    public CreateUpdateAddressDto(String country, String city,
                                  String street, String house, String flat) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    private String city;
    private String street;
    private String house;
    private String flat;
    private String userId;
}
