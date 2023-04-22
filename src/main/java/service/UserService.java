package service;

import dao.UserDao;
import dao.filter.UserFilter;
import dto.user.CreateUserDto;
import dto.user.UserDto;
import entity.user.User;
import exception.ValidationException;
import lombok.NoArgsConstructor;
import mapper.user.CreateUserMapper;
import mapper.user.UserMapper;
import validator.CreateUserValidator;
import validator.Error;
import validator.ValidationResult;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByNameAdnPassword(email, password)
                .map(userMapper::mapFrom);
    }

    public UserDto create(CreateUserDto userDto) {
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid() || validateOnName(userDto, validationResult)
            || validateOnEmail(userDto, validationResult)) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(userDto);
        return userMapper.mapFrom(userDao.save(userEntity));
    }

    private boolean validateOnName(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userDao.findAll(new UserFilter(1,0, userDto.getName(),
                null, null, null, null));
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.login", "This login is already used"));
            return true;
        } else {
            return false;
        }
    }

    private boolean validateOnEmail(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userDao.findAll(new UserFilter(1,0, null,
                null, userDto.getEmail(), null, null));
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.email", "This email is already used"));
            return true;
        } else {
            return false;
        }
    }
}
