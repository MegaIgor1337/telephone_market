package market.model.repository;

import market.service.dto.IEmailUserDto;
import market.service.dto.INameUserDto;
import market.service.dto.IValidateUserInfoDto;
import market.model.entity.User;
import market.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Page<User> findAllBy(Pageable pageable);

    List<INameUserDto> findAllByUsernameNotNull();

    List<IEmailUserDto> findAllByEmailNotNull();

    List<IValidateUserInfoDto> findAllByUsernameNotNullAndEmailNotNull();
    Page<User> findAllByRole(Role role, Pageable pageable);

    @Modifying(clearAutomatically = true)
    void deleteAllById(Long id);

    Optional<User> findByUsername(String username);

}
