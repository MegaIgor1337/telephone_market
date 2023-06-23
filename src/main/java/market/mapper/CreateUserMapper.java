package market.mapper;

import market.dto.CreateUserDto;
import market.dto.UpdateImageUserDto;
import market.entity.User;
import market.enums.Gender;
import market.enums.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

import static market.util.StringContainer.SPRING;


@Component
public class CreateUserMapper  {

    public User map(CreateUserDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    public UpdateImageUserDto map(User user, MultipartFile image) {
        return new UpdateImageUserDto(
                user.getName(),
                user.getPassword(),
                user.getPassportNo(),
                user.getEmail(),
                String.valueOf(user.getRole()),
                String.valueOf(user.getGender()),
                image,
                user.getBalance(),
                user.getId());
    }

    private void copy(CreateUserDto object, User user) {
        if (object instanceof UpdateImageUserDto) {
            user.setId(((UpdateImageUserDto) object).getId());
        }
        user.setName(object.getName());
        user.setPassword(object.getPassword());
        user.setEmail(object.getEmail());
        user.setPassportNo(object.getPassportNo());
        user.setRole(Role.valueOf(object.getRole()));
        user.setBalance(object.getBalance());
        user.setGender(Gender.valueOf(object.getGender()));

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }
}
