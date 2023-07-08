package project.service;

import lombok.RequiredArgsConstructor;
import market.dto.CreateUserDto;
import market.mapper.UserMapper;
import market.repository.UserRepository;
import market.service.UserService;
import org.junit.jupiter.api.Test;
import project.IntegrationTestBase;
import project.annotation.IT;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
public class UserServiceTest extends IntegrationTestBase {
    private final Long id = 1L;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;




    @Test
    void testSetNewName() {
        var user = userRepository.findById(id).orElse(null);
        userService.setNewLogin(userMapper.userToUserDto(user),
                "Andrey");
        assertThat(user.getUsername()).isEqualTo("Andrey");
    }


    @Test
    void setNewEmail() {
        var user = userRepository.findById(id).orElse(null);
        userService.setNewEmail(userMapper.userToUserDto(user),
                "fdlfdfkdlf@mail.ru");
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
