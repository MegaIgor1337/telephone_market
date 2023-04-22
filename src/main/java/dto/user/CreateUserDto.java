package dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String name;
    String password;
    String passportNo;
    String email;
    String role;
    String gender;

}