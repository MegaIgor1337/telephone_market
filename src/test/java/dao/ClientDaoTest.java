package dao;

import entity.address.Address;
import entity.client.Client;
import filter.ClientFilter;
import junit.framework.TestCase;

import java.util.List;
import java.util.Optional;

public class ClientDaoTest extends TestCase {
    private static final ClientDao clientDao = ClientDao.getInstance();

    public void testUpdate() {
        Client client = new Client(1L, "Igor1337", "kfgkfg", "KH274577", new Address(
                1L, "Belarus", "Minsk", "Pushkinstaya", "124  ", "14a"
        ), "tawerka228321@mail.ru");
        assertTrue(clientDao.update(client));
    }

    public void testFindById() {
        if (clientDao.findById(3L).isPresent()) {
            assertEquals(new Client(3L, "Gennadiy22", "boring", "HH333222",
                    new Address(3L, "Belarus", "Grodno", "Oginskogo", "4a   ", "14   "),
                    "goodjob@gmail.com"), clientDao.findById(3L).get());
        } else {
            fail();
        }
    }


    public void testTestFindAll() {
        ClientFilter filter = new ClientFilter(3, 0, "Igor", null, null, null);
        List<Client> clients = clientDao.findAll(filter);
        assertEquals(Optional.of(1L), Optional.of(clients.get(0).getId()));

    }


    public void testSave() {
        Client client = new Client(null, "Anna", "gkgkg", "HH333252",
                new Address(null, "Litva", "Vilnus", "Svobody", "424", "33"),
                "kgkgkgkg@mail.ru");
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