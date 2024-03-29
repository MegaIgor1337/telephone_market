package market.service.dto;


import market.model.enums.Gender;
import market.model.enums.Role;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  UserDto {
    private Long id;
    private String username;
    private String password;
    private String passportNo;
    private String email;
    private Role role;
    private Gender gender;
    private BigDecimal balance;
    private String image;
    private List<AddressDto> addresses;

}
