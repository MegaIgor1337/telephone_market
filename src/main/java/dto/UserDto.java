package dto;


import entity.enums.Gender;
import entity.enums.Role;
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
