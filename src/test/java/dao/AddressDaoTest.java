package dao;

import entity.address.Address;
import dao.filter.AddressFilter;
import junit.framework.TestCase;

public class AddressDaoTest extends TestCase {
    private static final AddressDao addressDao = AddressDao.getInstance();

    public void testUpdate() {
        assertTrue(addressDao.update(
                        new Address(
                                1L, "Belarus", "Minsk", "Pushkinstaya", "124", "14a")
                )
        );
    }

    public void testFindById() {
        if (addressDao.findById(2L).isPresent()) {
            assertEquals(new Address(
                            2L, "Russia", "Moscow", "Moscow", "321  ", "31   "
                    ),
                    addressDao.findById(2L).get());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        AddressFilter addressFilter = new AddressFilter(
                1, 0, "Belarus", "Minsk", null, null, null
        );
        assertEquals(new Address(
                        1L, "Belarus", "Minsk", "Pushkinstaya", "124  ", "14a  "),
                addressDao.findAll(addressFilter).get(0)
        );
    }


    public void testSave() {
        Address address = new Address(null, "Poland", "Warsaw",
                "Polskaya", "33", "");
        address = addressDao.save(address);
        assertEquals(addressDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(addressDao.findAll().size() - 1).getId(), address.getId());
    }

    public void testDelete() {
        assertTrue(addressDao.delete(addressDao.findAll().stream()
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(addressDao.findAll().size() - 1).getId()));
    }
}