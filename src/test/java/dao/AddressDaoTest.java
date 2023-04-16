package dao;

import entity.address.Address;
import dao.filter.AddressFilter;
import entity.user.Gender;
import entity.user.Role;
import entity.user.User;
import junit.framework.TestCase;

public class AddressDaoTest extends TestCase {
    private final AddressDao addressDao = AddressDao.getInstance();
    private final UserDao userDao = UserDao.getInstance();




    public void testUpdate() {
        assertTrue(addressDao.update(
                        new Address(
                                1L, "Belarus", "Minsk", "Pushkinstaya",
                                "124", "14a", new User(
                                1L, "Igor1337", "kfgkfg", "KH274577",
                                "tawerka228321@mail.ru", Role.USER, Gender.MALE
                        )
                        )
                )
        );
    }

    public void testFindById() {
        if (addressDao.findById(2L).isPresent()) {
            assertEquals(new Address(
                            2L, "Russia", "Moscow", "Moscow", "321  ", "31   ",
                            new User(
                                    2L, "Maksim321", "1234567",
                                    "FG232445", "wowowow@mail.ru", Role.USER, Gender.MALE
                            )
                    ),
                    addressDao.findById(2L).get());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        AddressFilter addressFilter = new AddressFilter(
                1, 0, "Belarus", "Minsk", null, null, null, null
        );
        assertEquals(new Address(
                        1L, "Belarus", "Minsk", "Pushkinstaya", "124  ", "14a  ",
                        new User(
                                1L, "Igor1337", "kfgkfg", "KH274577",
                                "tawerka228321@mail.ru", Role.USER, Gender.MALE
                        )
                ),
                addressDao.findAll(addressFilter).get(0)
        );
    }



}