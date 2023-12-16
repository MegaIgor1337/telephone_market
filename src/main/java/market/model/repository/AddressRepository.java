package market.model.repository;

import market.model.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>  {
     @Query("SELECT a FROM Address a WHERE a.user.id = :id AND a.deleted = false")
     Page<Address> findByUserIdAndDeletedFalse(@Param("id") Long id, Pageable pageable);
     List<Address> findByUserId(Long id);

}
