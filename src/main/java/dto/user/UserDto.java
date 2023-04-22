package dto.user;


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
    Long id;
    String name;
    String password;
    String passportNo;
    String email;
    Role role;
    Gender gender;
}
