package project.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import market.model.entity.User;
import market.model.enums.Gender;
import market.model.enums.Role;
import market.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.annotation.IT;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static market.service.util.ConstantContainer.ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;


@RequiredArgsConstructor
@IT
public class UserRepositoryTest  {
    private final EntityManager entityManager;

    private final UserRepository userRepository;


    @Test
    public void testSave() {
        User user = User.builder()
                .username("Inna")
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
        List<String> fullNames = results.stream().map(User::getUsername).collect(toList());
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
    public void testFind() {
        User user = userRepository.findById(1L).isPresent()
                ? userRepository.findById(1L).get() : null;
        assert user != null;
        assertThat(user.getUsername()).isEqualTo("Igor");
    }

    @Test
    public void testPage() {
        var pageable = PageRequest.of(1,2, Sort.by(ID));
        var users = userRepository.findAll(pageable);
        assertFalse(users.isEmpty());
        assertThat(users).hasSize(2);
    }
}
