package project.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import market.ApplicationRunner;
import market.entity.User;
import market.enums.Gender;
import market.enums.Role;
import market.repository.AddressRepository;
import market.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.TestApplicationRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;


@Transactional
@SpringBootTest(classes = {ApplicationRunner.class,TestApplicationRunner.class})
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
public class UserRepositoryTest {
    private final EntityManager entityManager;

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;









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

    @Test
    public void testPage() {
        var pageable = PageRequest.of(1,2, Sort.by("id"));
        var users = userRepository.findAll(pageable);
        assertFalse(users.isEmpty());
        assertThat(users).hasSize(2);
    }



}
