package market.service.impl;

import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import market.exception.ValidationException;
import market.model.entity.Address;
import market.model.entity.User;
import market.model.enums.Role;
import market.model.repository.AddressRepository;
import market.model.repository.UserRepository;
import market.service.ImageService;
import market.service.UserService;
import market.service.dto.*;
import market.service.mapper.CreateUserMapper;
import market.service.mapper.UserMapper;
import market.service.validator.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static market.service.util.ConstantContainer.*;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {
    private final AddressRepository addressRepository;
    private final CreateUserValidator createUserValidator;
    private final CreateUserMapper createUserMapper;
    private final UsernameValidator usernameValidator;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final LoginUserValidator loginUserValidator;
    private final UserRepository userRepository;
    private final UserEmailValidator userEmailValidator;
    private final BalanceValidator balanceValidator;
    private final UserPassportNoValidator userPassportNoValidator;

    @Override
    public Optional<UserDto> login(String login, String password) {
        Optional<UserDto> userDto = userRepository.findAll()
                .stream()
                .filter(it -> it.getUsername()
                                      .equals(login)
                              && it.getPassword().equals(password))
                .map(userMapper::userToUserDto).findFirst();
        var validationResult = loginUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return userDto;
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.userToUserDto(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    private List<IValidateUserInfoDto> getValidateInfo() {
        return userRepository.findAllByUsernameNotNullAndEmailNotNull();
    }

    @Override
    public void validCreateUserDto(CreateUserDto createUserDto) {
        createUserValidator.putUserValidationInfo(getValidateInfo());
        var validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
    }


    @Override
    @Transactional
    public UserDto create(CreateUserDto userDto) {
        validCreateUserDto(userDto);
        userDto.setBalance(BigDecimal.ZERO);
        return Optional.of(userDto)
                .map(dto -> {
                     return createUserMapper.map(dto);
                })
                .map(userRepository::save)
                .map(userMapper::userToUserDto)
                .orElseThrow();
    }


    private List<INameUserDto> getAllNames() {
        return userRepository.findAllByUsernameNotNull();
    }

    private List<IEmailUserDto> getAllEmails() {
        return userRepository.findAllByEmailNotNull();
    }


    @Override
    @Transactional
    public void setNewLogin(UserDto userDto, String newLogin) {
        usernameValidator.putUsersNames(getAllNames());
        var validationResultName = usernameValidator.isValid(newLogin);
        if (!validationResultName.isValid()) {
            throw new ValidationException(validationResultName.getErrors());
        }
        var userEntity = userMapper.userDtotoUser(userDto);
        userEntity.setUsername(newLogin);
        setAddressAndSave(userEntity);
    }

    private void setAddressAndSave(User user) {
        var userAddresses = addressRepository.findByUserId(user.getId());
        user.setAddresses(userAddresses);
        userRepository.saveAndFlush(user);
    }


    @Override
    @Transactional
    public void setNewPassportNo(UserDto userDto, String newPassportNo) {
        var validationResultPassportNo = userPassportNoValidator.isValid(newPassportNo);
        if (!validationResultPassportNo.isValid()) {
            throw new ValidationException(validationResultPassportNo.getErrors());
        }
        var userEntity = userMapper.userDtotoUser(userDto);
        userEntity.setPassportNo(newPassportNo);
        setAddressAndSave(userEntity);
    }

    @Override
    @Transactional
    public void setNewEmail(UserDto userDto, String newEmail) {
        userEmailValidator.putEmails(getAllEmails());
        var validationResultEmail = userEmailValidator.isValid(newEmail);
        if (!validationResultEmail.isValid()) {
            throw new ValidationException(validationResultEmail.getErrors());
        }
        var userEntity = userMapper.userDtotoUser(userDto);
        userEntity.setEmail(newEmail);
        setAddressAndSave(userEntity);
    }

    @Transactional
    public Optional<UserDto> updateImage(Long id, MultipartFile image) {
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(image);
                    Optional.ofNullable(image)
                            .filter(Predicate.not(MultipartFile::isEmpty))
                            .ifPresent(avatar -> entity.setImage(avatar.getOriginalFilename()));
                    return entity;
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::userToUserDto);
    }


    /*
    *  Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    * */


    /*
    *  return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(image);
                    return createUserMapper.map(createUserDto);
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::userToUserDto);
    * */


    @Override
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
            log.info("User {} putted money {}", id, money);
            return Optional.of(userMapper.userToUserDto(userRepository
                    .saveAndFlush(userDto.get())));
        }
        return Optional.empty();
    }


    @Override
    public UserDto getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        String username = authentication.getName();
        return userRepository.findByUsername(username).map(userMapper::userToUserDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @Override
    public Page<UserDto> getUsersByPredicates(UserFilter filter, Integer page) {
        Specification<User> specifications = getSpecifications(filter);
        var users = userRepository.findAll(specifications, PageRequest.of(page, PAGE_SIZE
        ));
        return users.map(userMapper::userToUserDto);
    }

    private Specification<User> getSpecifications(UserFilter filter) {
        Specification<User> specification = Specification.where(null);
        specification = specification
                .and((root, query, cb) -> cb.equal(root.get(ROLE), Role.USER));
        if (filter.getUsername() != null && !filter.getUsername().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.like(root.get(USER_NAME), filter.getUsername()));
        }
        if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.like(root.get(EMAIL), filter.getEmail()));
        }
        if (filter.getPassportNo() != null && !filter.getPassportNo().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.like(root.get(PASSPORT_NO), filter.getPassportNo()));
        }
        if (filter.getPassword() != null && !filter.getPassword().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.like(root.get(PASSWORD), filter.getPassword()));
        }
        if (filter.getGender() != null && !filter.getGender().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(GENDER), filter.getGender()));
        }
        if (filter.getCountry() != null && !filter.getCountry().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> {
                        Join<User, Address> addressJoin = root.join(ADDRESSES);
                        return cb.like(addressJoin.get(COUNTRY), filter.getCountry());
                    });
        }
        if (filter.getCity() != null && !filter.getCity().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> {
                        Join<User, Address> addressJoin = root.join(ADDRESSES);
                        return cb.like(addressJoin.get(CITY), filter.getCity());
                    });
        }
        if (filter.getStreet() != null && !filter.getStreet().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> {
                        Join<User, Address> addressJoin = root.join(ADDRESSES);
                        return cb.like(addressJoin.get(STREET), filter.getStreet());
                    });
        }
        if (filter.getHouse() != null && !filter.getHouse().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> {
                        Join<User, Address> addressJoin = root.join(ADDRESSES);
                        return cb.like(addressJoin.get(HOUSE), filter.getHouse());
                    });
        }
        if (filter.getFlat() != null && !filter.getFlat().isBlank()) {
            specification = specification
                    .and((root, query, cb) -> {
                        Join<User, Address> addressJoin = root.join(ADDRESSES);
                        return cb.like(addressJoin.get(FLAT), filter.getFlat());
                    });
        }
        return specification;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteAllById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + username));
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    public UserDto getUserByName(String username) {
        return userRepository.findByUsername(username).map(userMapper::userToUserDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public String getUsernameByID(Long id) {
        return userRepository.findById(id).map(User::getUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
