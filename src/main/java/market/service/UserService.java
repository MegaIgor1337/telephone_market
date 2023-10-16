package market.service;

import market.dto.CreateUserDto;
import market.dto.UserDto;
import market.dto.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> login(String login, String password);
    UserDto findById(Long id);
    UserDto create(CreateUserDto userDto);
    void setNewLogin(UserDto userDto, String newLogin);
    void setNewPassportNo(UserDto userDto, String newPassportNo);
    void setNewEmail(UserDto userDto, String newEmail);
    Optional<UserDto> putMoney(Long id, String money);
    UserDto getCurrentUser(Authentication authentication);
    Page<UserDto> getUsersByPredicates(UserFilter filter, Integer page);
    void deleteUser(Long id);
    UserDto getUserByName(String username);
    String getUsernameByID(Long id);
    Optional<byte[]> findAvatar(Long id);
    Optional<UserDto> updateImage(Long id, MultipartFile image);
    void validCreateUserDto(CreateUserDto createUserDto);
}
