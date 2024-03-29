package market.service.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    private String rawPassword;
    private String passportNo;
    private String email;
    private String gender;
    private BigDecimal balance;
    private MultipartFile image;

}