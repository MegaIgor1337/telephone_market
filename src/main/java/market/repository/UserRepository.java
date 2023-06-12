package market.repository;

import market.dto.IEmailUserDto;
import market.dto.INameUserDto;
import market.dto.IValidateUserInfoDto;
import market.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllBy(Pageable pageable);

    List<INameUserDto> findAllByNameNotNull();

    List<IEmailUserDto> findAllByEmailNotNull();

    List<IValidateUserInfoDto> findAllByNameNotNullAndEmailNotNull();

}
