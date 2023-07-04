package project.service;

import lombok.RequiredArgsConstructor;
import market.dto.CreateUserDto;
import market.mapper.UserMapper;
import market.repository.UserRepository;
import market.service.UserService;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class UserServiceTest {
    private final Long id = 1L;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Test
    void testLogin() {
        var result = userService.login("Maksim321", "1234567");
        assertTrue(result.isPresent());
    }

    @Test
    void testCreate() {
        var createUserDto = CreateUserDto.builder()
                .username("kkk")
                .email("fdfdfd@mail.ru")
                .rawPassword("jkfkdfjkdfjkdfjkfjJJJJ2")
                .passportNo("FF542145")
                .gender("MALE")
                .build();
        var result = userService.create(createUserDto);
        assertThat(result.getId()).isEqualTo(5L);
    }

    @Test
    void testSetNewName() {
        var user = userRepository.findById(id).orElse(null);
        userService.setNewLogin(userMapper.userToUserDto(user),
                "Andrey");
        assertThat(user.getUsername()).isEqualTo("Andrey");
    }

    @Test
    void setNewPassword() {
        var user = userRepository.findById(id).orElse(null);
        userService.setNewPassword(userMapper.userToUserDto(user),
                "FjfjfsjdlJJJJ32","1323123123JKJKJkjkj4");
        assertThat(user.getPassword()).isEqualTo("1323123123JKJKJkjkj4");
    }

    @Test
    void setNewEmail() {
        var user = userRepository.findById(id).orElse(null);
        userService.setNewEmail(userMapper.userToUserDto(user),
                "fdlfdfkdlf@mail.ru", "FjfjfsjdlJJJJ32");
        assertThat(user.getEmail()).isEqualTo("fdlfdfkdlf@mail.ru");
    }

    @Test
    void setNewPassportNo() {
        var user = userRepository.findById(id).orElse(null);
        userService.setNewPassportNo(userMapper.userToUserDto(user),
                "KK432411");
        assertThat(user.getPassportNo()).isEqualTo("KK432411");
    }


    @Test
    void putMoney() {
        userService.putMoney(id, "15.75");
        var result = userRepository.findById(id).map(userMapper::userToUserDto);
        result.ifPresent(u -> assertThat(u.getBalance())
                .isEqualTo(new BigDecimal("15.75")));
    }
}
