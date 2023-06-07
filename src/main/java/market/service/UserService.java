package market.service;

import market.repository.UserRepository;
import market.dto.CreateUserDto;
import market.dto.UserDto;
import market.entity.User;
import market.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import market.mapper.user.CreateUserMapper;
import market.mapper.user.UserDtoMapper;
import market.mapper.user.UserMapper;
import org.springframework.stereotype.Service;
import market.validator.CreateUserValidator;
import market.validator.Error;
import market.validator.ValidationResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {



    private final CreateUserValidator createUserValidator;
    private final CreateUserMapper createUserMapper;
    private final UserMapper userMapper;
    private final UserDtoMapper userDtoMapper;
    private final UserRepository userRepository;

    public Optional<UserDto> login(String login, String password) {
        return userRepository.findAll()
                .stream()
                .filter(it -> it.getName()
                                      .equals(login)
                                                   && it.getPassword().equals(password))
                .map(userMapper::mapFrom).findFirst();
    }

    public UserDto create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid() || validateOnName(userDto, validationResult)
            || validateOnEmail(userDto, validationResult)) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(userDto);
        userEntity.setBalance(BigDecimal.valueOf(0.0));
        return userMapper.mapFrom(userRepository.save(userEntity));
    }

    public void setNewLogin(UserDto userDto, String newLogin) {
        userDto.setName(newLogin);
        userRepository.save(userDtoMapper.mapFrom(userDto));
    }
    private boolean validateOnName(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userRepository.findAll().stream()
                .filter(it -> it.getName().equals(userDto.getName())).collect(Collectors.toList());
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.login", "This login is already used"));
            return true;
        } else {
            return false;
        }
    }

    public boolean validatePassword(String password) {
        Pattern patternForPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$");
        Matcher matcherForPassword = patternForPassword.matcher(password);
        return matcherForPassword.find();
    }

    public boolean validateOnNewName(String newName) {
        List<User> users = userRepository.findAll().stream()
                .filter(it -> it.getName().equals(newName)).collect(Collectors.toList());
        return users.isEmpty();
    }

    public boolean validateOnPassword(UserDto userDto, String enteredPassword) {
        return userDto.getPassword().equals(enteredPassword);
    }

    private boolean validateOnEmail(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userRepository.findAll().stream()
                .filter(it -> it.getEmail().equals(userDto.getEmail())).collect(Collectors.toList());
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.email", "This email is already used"));
            return true;
        } else {
            return false;
        }
    }

    public void setNewPassword(UserDto userDto, String password) {
        userDto.setPassword(password);
        userRepository.save(userDtoMapper.mapFrom(userDto));
    }

    public boolean validatePassportNo(String passportNo) {
        Pattern patternForPassportNo = Pattern.compile("^[a-zA-Z]{2}\\d{6}$");
        Matcher matcherForPassportNo = patternForPassportNo.matcher(passportNo);
        return matcherForPassportNo.find();
    }

    public boolean validateEmail(String email) {
        Pattern patternForEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcherForEmail = patternForEmail.matcher(email);
        return matcherForEmail.find();
    }

    public void setNewPassportNo(UserDto userDto, String newPassportNo) {
        userDto.setPassportNo(newPassportNo);
        userRepository.save(userDtoMapper.mapFrom(userDto));
    }

    public void setNewEmail(UserDto userDto, String newEmail) {
        userDto.setEmail(newEmail);
        userRepository.save(userDtoMapper.mapFrom(userDto));
    }

}
