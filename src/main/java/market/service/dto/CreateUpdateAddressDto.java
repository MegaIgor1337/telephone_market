package market.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUpdateAddressDto  implements ICreateAddressDto {
    private String id;
    private String country;
    private String street;
    private String house;
    private String flat;
    private String city;
    private String userId;
}
