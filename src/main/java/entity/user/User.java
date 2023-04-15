package entity.user;

import entity.address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String password;
    private String passportNo;
    private Address address;
    private String email;
    private Role role;
    private Gender gender;

}
