package dao;


import entity.Address;
import entity.Comment;
import entity.User;
import entity.enums.Gender;
import entity.enums.Role;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import util.HibernateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utils.TestDataImporter;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class DaoTest {
    private final CommentDao commentDao = CommentDao.getINSTANCE();
    private final AddressDao addressDao = AddressDao.getINSTANCE();
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final UserDao userDao = UserDao.getINSTANCE();
    private  Integer  sizeUsers;
    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
        System.out.println();
    }
    @AfterAll
    public void close() {
        sessionFactory.close();
    }
    @Test
    public void testAddressesFindByUserId() {
        List<Address> addresses = addressDao.findByUserId(sessionFactory, 1L);
        assertThat(1).isEqualTo(addresses.size());
    }

    @Test
    public void testFindByUserId() {
        List<Comment> comments = commentDao.findByUserId(sessionFactory, 2L);
        assertThat(2).isEqualTo(comments.size());
    }

    @Test
    public void testSave() {
        User user = User.builder()
                .name("Inna")
                .password("Fjllwe2")
                .email("jgdkjgd@mail.ru")
                .role(Role.USER)
                .gender(Gender.FEMALE)
                .passportNo("JJ425112")
                .balance(BigDecimal.valueOf(0))
                .build();
        userDao.save(sessionFactory, user);
        assertThat(userDao.get(sessionFactory).size()).isEqualTo(5);
    }

    @Test
    public void testFindAll() {
        List<User> results = userDao.get(sessionFactory);
        sizeUsers = results.size();
        List<String> fullNames = results.stream().map(User::getName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Igor",
                "Maksim321",
                "Gennadiy22",
                "Georgiy");
    }

    @Test
    public void testUpdate() {
        User user = userDao.find(sessionFactory, 1L).isPresent()
                ? userDao.find(sessionFactory, 1L).get()
                : fail("user is null");
        user.setEmail("Ghsdsdst4@mail.ru");
        userDao.update(sessionFactory, user);
        User user1 = userDao.find(sessionFactory, 1L).isPresent()
                ? userDao.find(sessionFactory, 1L).get()
                : fail("user1 is null");
        assertThat(user1.getEmail()).isEqualTo("Ghsdsdst4@mail.ru");
    }

    @Test
    public void testDelete() {
        userDao.delete(userDao.get(sessionFactory).stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(userDao.get(sessionFactory).size() - 1).getId(), sessionFactory);
        if (sizeUsers !=  userDao.get(sessionFactory).size())
            fail("user has not been deleted");
    }

    @Test
    public void testFind() {
        User user = userDao.find(sessionFactory, 1L).isPresent()
                ? userDao.find(sessionFactory, 1L).get() : null;
        assertThat(user.getName()).isEqualTo("Igor");
    }

}
