package market.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
public class UpdateImageUserDto extends CreateUserDto {
    @Getter
    @Setter
    private Long id;

    public UpdateImageUserDto(String name, String password, String passportNo, String email,
                              String role, String gender, MultipartFile image, BigDecimal balance, Long id) {
        super(name, password, passportNo, email, role, gender, image, balance);
        this.id = id;
    }
}
