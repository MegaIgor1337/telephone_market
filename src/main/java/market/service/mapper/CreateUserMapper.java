package market.service.mapper;

import lombok.RequiredArgsConstructor;
import market.service.dto.CreateUserDto;
import market.model.entity.User;
import market.model.enums.Gender;
import market.model.enums.Role;
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
        user.setImage("img.png");
        return user;
    }


    public CreateUserDto map(User user, MultipartFile multipartFile) {
        var createUserDto = new CreateUserDto();
        createUserDto.setImage(multipartFile);
        createUserDto.setBalance(user.getBalance());
        createUserDto.setGender(user.getGender().toString());
        createUserDto.setEmail(user.getEmail());
        createUserDto.setPassportNo(user.getPassportNo());
        return createUserDto;
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

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));

    }



}
