package dao;

import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserDaoTest extends TestCase {
    private static final UserDao userDao = UserDao.getINSTANCE();

    public void testUpdate() {
        User client = User.builder()
                .id(1L)
                .name("Igor1337")
                .password("kfgkfg")
                .passportNo("KH274577")
                .email("tawerka228321@mail.ru")
                .role(Role.USER).gender(Gender.MALE).balance(BigDecimal.valueOf(0)).build();

        assertTrue(userDao.update(client));
    }

   

}