package market.mapper;

import lombok.RequiredArgsConstructor;
import market.dto.CreateUserDto;
import market.entity.User;
import market.enums.Gender;
import market.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;


@Component
@RequiredArgsConstructor
public class CreateUserMapper  {
    private final PasswordEncoder passwordEncoder;


    public User map(CreateUserDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }



    private void copy(CreateUserDto object, User user) {
        user.setUsername(object.getUsername());
        user.setPassword(object.getRawPassword());
        user.setEmail(object.getEmail());
        user.setPassportNo(object.getPassportNo());
        user.setRole(Role.USER);
        user.setBalance(object.getBalance());
        user.setGender(Gender.valueOf(object.getGender()));

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);


    }
}
