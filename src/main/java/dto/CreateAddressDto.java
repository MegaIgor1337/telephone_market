package dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAddressDto {
    private String id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
    private String userId;
}
