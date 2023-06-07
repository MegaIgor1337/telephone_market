package market.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
    private String name;
    private String password;
    private String passportNo;
    private String email;
    private String role;
    private String gender;
}