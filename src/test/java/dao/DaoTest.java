package dao;


import entity.Address;
import entity.Comment;
import entity.User;
import entity.enums.Gender;
import entity.enums.Role;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import util.HibernateUtil;
import utils.TestDataImporter;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class DaoTest {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final CommentRepository commentRepository = new CommentRepository(HibernateUtil
            .getSessionFromFactory(sessionFactory));
    private final AddressRepository addressRepository = new AddressRepository(HibernateUtil
            .getSessionFromFactory(sessionFactory));
    private final UserRepository userRepository = new UserRepository(HibernateUtil
            .getSessionFromFactory(sessionFactory));
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
        List<Address> addresses = addressRepository.findByUserId(1L);
        assertThat(1).isEqualTo(addresses.size());
    }

    @Test
    public void testFindByUserId() {
        List<Comment> comments = commentRepository.findByUserId(2L);
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
        userRepository.save(user);
        assertThat(userRepository.findAll().size()).isEqualTo(5);
    }

    @Test
    public void testFindAll() {
        List<User> results = userRepository.findAll();
        sizeUsers = results.size();
        List<String> fullNames = results.stream().map(User::getName).collect(toList());
        assertThat(fullNames).containsExactlyInAnyOrder("Igor",
                "Maksim321",
                "Gennadiy22",
                "Georgiy");
    }

    @Test
    public void testUpdate() {
        User user = userRepository.findById(1L).isPresent()
                ? userRepository.findById(1L).get()
                : fail("user is null");
        user.setEmail("Ghsdsdst4@mail.ru");
        userRepository.update(user);
        User user1 = userRepository.findById(1L).isPresent()
                ? userRepository.findById(1L).get()
                : fail("user1 is null");
        assertThat(user1.getEmail()).isEqualTo("Ghsdsdst4@mail.ru");
    }

    @Test
    public void testDelete() {
        userRepository.delete(userRepository.findAll().stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .toList().get(userRepository.findAll().size() - 1).getId());
        if (sizeUsers != userRepository.findAll().size())
            fail("user has not been deleted");
    }

    @Test
    public void testFind() {
        User user = userRepository.findById(1L).isPresent()
                ? userRepository.findById(1L).get() : null;
        assertThat(user.getName()).isEqualTo("Igor");
    }

}
