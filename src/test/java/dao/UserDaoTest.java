package dao;

import entity.address.Address;
import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import dao.filter.UserFilter;
import junit.framework.TestCase;

import java.util.List;
import java.util.Optional;

public class UserDaoTest extends TestCase {
    private static final UserDao clientDao = UserDao.getInstance();

    public void testUpdate() {
        User client = new User(1L, "Igor1337", "kfgkfg", "KH274577", new Address(
                1L, "Belarus", "Minsk", "Pushkinstaya", "124  ", "14a"
        ), "tawerka228321@mail.ru", Role.USER, Gender.MALE);
        assertTrue(clientDao.update(client));
    }

    public void testFindById() {
        if (clientDao.findById(3L).isPresent()) {
            assertEquals(new User(3L, "Gennadiy22", "boring", "HH333222",
                    new Address(3L, "Belarus", "Grodno",
                            "Oginskogo", "4a   ", "14   "),
                    "goodjob@gmail.com", Role.USER, Gender.MALE), clientDao.findById(3L).get());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        UserFilter filter = new UserFilter(3, 0, "Igor",
                null, null, null, null, null);
        List<User> clients = clientDao.findAll(filter);
        assertEquals(Optional.of(1L), Optional.of(clients.get(0).getId()));

    }


    public void testSave() {
        User client = new User(null, "Anna", "gkgkg", "HH333252",
                new Address(null, "Litva", "Vilnus", "Svobody", "424", "33"),
                "kgkgkgkg@mail.ru", Role.USER, Gender.MALE);
        clientDao.save(client);
        assertEquals(clientDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(clientDao.findAll().size() - 1).getId(), client.getId());
    }


    public void testDelete() {
        assertTrue(clientDao.delete(clientDao.findAll().stream()
                .sorted(((o1, o2) -> Long.compare(o1.getId(), o2.getId())))
                .toList().get(clientDao.findAll().size() - 1).getId()));
    }
}