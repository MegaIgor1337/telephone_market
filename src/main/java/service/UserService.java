package service;

import dao.UserRepository;
import dto.CreateUserDto;
import dto.UserDto;
import entity.User;
import exception.ValidationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mapper.user.CreateUserMapper;
import mapper.user.UserMapper;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
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
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final UserRepository userRepository = new UserRepository(HibernateUtil
            .getSessionFromFactory(sessionFactory));
    public static UserService getInstance() {
        return INSTANCE;
    }

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

    private boolean validateOnName(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userRepository.findAll().stream()
                .filter(it -> it.getName().equals(userDto.getName())).toList();
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.login", "This login is already used"));
            return true;
        } else {
            return false;
        }
    }

    private boolean validateOnEmail(CreateUserDto userDto, ValidationResult validationResult) {
        List<User> users = userRepository.findAll().stream()
                .filter(it -> it.getEmail().equals(userDto.getEmail())).toList();
        if (!users.isEmpty()) {
            validationResult.add(Error.of("invalid.email", "This email is already used"));
            return true;
        } else {
            return false;
        }
    }
}
