package project.dao;


import jakarta.persistence.EntityManager;
import market.ApplicationRunner;
import market.entity.Address;
import market.entity.Comment;
import market.entity.User;
import market.enums.Gender;
import market.enums.Role;
import market.repository.AddressRepository;
import market.repository.CommentRepository;
import market.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@Transactional
@SpringBootTest(classes = {ApplicationRunner.class,TestApplicationRunner.class})
@ExtendWith(SpringExtension.class)
public class DaoTest {
    private final EntityManager entityManager;

    private final AddressRepository addressRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public DaoTest(EntityManager entityManager,
                   AddressRepository addressRepository, CommentRepository commentRepository,
                   UserRepository userRepository) {
        this.entityManager = entityManager;
        this.addressRepository = addressRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
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
        entityManager.flush();
        assertThat(userRepository.findAll().size()).isEqualTo(5);
    }

    @Test
    public void testFindAll() {
        List<User> results = userRepository.findAll();
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
        userRepository.save(user);
        User user1 = userRepository.findById(1L).isPresent()
                ? userRepository.findById(1L).get()
                : fail("user1 is null");
        assertThat(user1.getEmail()).isEqualTo("Ghsdsdst4@mail.ru");
    }

    @Test
    public void testDelete() {
        userRepository.delete(userRepository.findAll().stream().sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .collect(Collectors.toList()).get(userRepository.findAll().size() - 1));
        entityManager.flush();
    }

    @Test
    public void testFind() {
        User user = userRepository.findById(1L).isPresent()
                ? userRepository.findById(1L).get() : null;
        assertThat(user.getName()).isEqualTo("Igor");
    }

}
