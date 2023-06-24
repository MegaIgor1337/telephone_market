package market.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilter {
    private String name;
    private String password;
    private String email;
    private String passportNo;
    private String gender;
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
}
