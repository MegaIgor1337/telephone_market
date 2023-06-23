package market.service;

import lombok.SneakyThrows;
import market.dto.*;
import market.entity.User;
import market.repository.UserRepository;
import market.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import market.mapper.CreateUserMapper;
import market.mapper.UserMapper;
import market.validator.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserPasswordValidator userPasswordValidator;
    private final ImageService imageService;
    private final CreateUserValidator createUserValidator;
    private final CreateUserMapper createUserMapper;
    private final UsernameValidator usernameValidator;
    private final UserMapper userMapper;
    private final LoginUserValidator loginUserValidator;
    private final UserRepository userRepository;
    private final UserEmailValidator userEmailValidator;
    private final BalanceValidator balanceValidator;
    private final UserPassportNoValidator userPassportNoValidator;
    public Optional<UserDto> login(String login, String password) {
        Optional<UserDto> userDto = userRepository.findAll()
                .stream()
                .filter(it -> it.getName()
                                      .equals(login)
                                                   && it.getPassword().equals(password))
                .map(userMapper::userToUserDto).findFirst();
        var validationResult = loginUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return userDto;
    }


    private List<IValidateUserInfoDto> getValidateInfo() {
        return userRepository.findAllByNameNotNullAndEmailNotNull();
    }

    @Transactional
    public UserDto create(CreateUserDto userDto) {
        createUserValidator.putUserValidationInfo(getValidateInfo());
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        userDto.setBalance(BigDecimal.ZERO);
        return  Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return createUserMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userMapper::userToUserDto)
                .orElseThrow();
    }

    @Transactional
    public UserDto setNewAvatar(Long id, MultipartFile image) {
        var user = userRepository.findById(id).orElseThrow();
        var updateUser = createUserMapper.map(user, image);
        return Optional.of(updateUser)
                .map(dto -> {
                    uploadImage(image);
                    return createUserMapper.map(dto);
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::userToUserDto)
                .orElseThrow();
    }

    private List<INameUserDto> getAllNames() {
        return userRepository.findAllByNameNotNull();
    }

    private List<IEmailUserDto> getAllEmails() {
        return userRepository.findAllByEmailNotNull();
    }

    @Transactional
    public void setNewLogin(UserDto userDto, String newLogin, String password) {
        validateOldPassword(userDto, password);
        usernameValidator.putUsersNames(getAllNames());
        var validationResultName = usernameValidator.isValid(newLogin);
        if (!validationResultName.isValid()) {
            throw new ValidationException(validationResultName.getErrors());
        }
        userDto.setName(newLogin);
        userRepository.saveAndFlush(userMapper.userDtotoUser(userDto));
    }

    @Transactional
    public void setNewPassword(UserDto userDto, String oldPassword, String newPassword) {
        validateOldPassword(userDto, oldPassword);
        var validationResultNewPassword = userPasswordValidator.isValid(newPassword);
        if (!validationResultNewPassword.isValid()) {
            throw new ValidationException(validationResultNewPassword.getErrors());
        }
        userDto.setPassword(newPassword);
        userRepository.save(userMapper.userDtotoUser(userDto));
    }

    private void validateOldPassword(UserDto userDto, String password) {
        var validationResultPassword = userPasswordValidator.isValid(userDto, password);
        if (!validationResultPassword.isValid()) {
            throw new ValidationException(validationResultPassword.getErrors());
        }
    }

    @Transactional
    public void setNewPassportNo(UserDto userDto, String newPassportNo, String password) {
        validateOldPassword(userDto, password);
        var validationResultPassportNo = userPassportNoValidator.isValid(newPassportNo);
        if (!validationResultPassportNo.isValid()) {
            throw new ValidationException(validationResultPassportNo.getErrors());
        }
        userDto.setPassportNo(newPassportNo);
        userRepository.save(userMapper.userDtotoUser(userDto));
    }

    @Transactional
    public void setNewEmail(UserDto userDto, String newEmail, String password) {
        validateOldPassword(userDto, password);
        userEmailValidator.putEmails(getAllEmails());
        var validationResultEmail = userEmailValidator.isValid(newEmail);
        if (!validationResultEmail.isValid()) {
            throw new ValidationException(validationResultEmail.getErrors());
        }
        userDto.setEmail(newEmail);
        userRepository.save(userMapper.userDtotoUser(userDto));
    }

    @Transactional
    public Optional<UserDto> putMoney(Long id, String money) {
        var validationResult = balanceValidator.isValid(money);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userDto = userRepository.findById(id);
        if (userDto.isPresent()) {
            var balance = userDto.get().getBalance();
            var newBalance = balance.add(new BigDecimal(money));
            userDto.get().setBalance(newBalance);
            return Optional.of(userMapper.userToUserDto(userRepository
                    .saveAndFlush(userDto.get())));
        }
        return Optional.empty();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if(!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
