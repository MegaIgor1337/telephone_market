package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String name;
    String password;
    String passportNo;
    String addressId;
    String email;
    String role;
    String gender;
}