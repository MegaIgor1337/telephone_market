package service;

import dao.UserDao;
import dto.user.CreateUserDto;
import dto.user.UserDto;
import entity.user.User;
import exception.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mapper.user.CreateUserMapper;
import mapper.user.UserMapper;
import validator.CreateUserValidator;
import validator.Error;
import validator.ValidationResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    @Getter
    private static final UserService INSTANCE = new UserService();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getINSTANCE();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String login, String password) {
        return userDao.get()
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
        return userMapper.mapFrom(userDao.find(userDao.save(userEntity)).get());
    }

    private boolean validateOnName(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userDao.get().stream()
                .filter(it -> it.getName().equals(userDto.getName())).toList();
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.login", "This login is already used"));
            return true;
        } else {
            return false;
        }
    }

    private boolean validateOnEmail(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userDao.get().stream()
                .filter(it -> it.getEmail().equals(userDto.getEmail())).toList();
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.email", "This email is already used"));
            return true;
        } else {
            return false;
        }
    }
}
