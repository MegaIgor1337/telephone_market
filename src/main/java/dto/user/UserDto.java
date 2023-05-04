package dto.user;


import dto.address.AddressDto;
import entity.address.Address;
import entity.user.Gender;
import entity.user.Role;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  UserDto {
    private Long id;
    private String name;
    private String password;
    private String passportNo;
    private String email;
    private Role role;
    private Gender gender;
    private BigDecimal balance;
}
