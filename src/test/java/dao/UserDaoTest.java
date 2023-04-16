package dao;

import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import dao.filter.UserFilter;
import junit.framework.TestCase;

import java.util.List;
import java.util.Optional;

public class UserDaoTest extends TestCase {
    private static final UserDao userDao = UserDao.getInstance();

    public void testUpdate() {
        User client = new User(1L, "Igor1337", "kfgkfg", "KH274577",
                "tawerka228321@mail.ru", Role.USER, Gender.MALE);
        assertTrue(userDao.update(client));
    }

    public void testFindById() {
        if (userDao.findById(3L).isPresent()) {
            assertEquals(new User(3L, "Gennadiy22", "boring", "HH333222",
                    "goodjob@gmail.com", Role.USER, Gender.MALE), userDao.findById(3L).get());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        UserFilter filter = new UserFilter(3, 0, "Igor",
                null, null, null, null);
        List<User> clients = userDao.findAll(filter);
        assertEquals(Optional.of(1L), Optional.of(clients.get(0).getId()));

    }



}