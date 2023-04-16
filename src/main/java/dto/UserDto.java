package dto;


import entity.address.Address;
import entity.user.Gender;
import entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String password;
    private String passportNo;
    private Address address;
    private String email;
    private Role role;
    private Gender gender;
}
