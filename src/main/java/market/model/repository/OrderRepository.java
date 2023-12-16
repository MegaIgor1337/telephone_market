package market.model.repository;

import market.model.entity.Order;
import market.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findAllByUserIdAndStatus(Long userId, OrderStatus orderStatus);
    List<Order> findByUserId(Long userId);
    List<Order> findAllByUserId(Long userId);
    List<Order> findAllByStatus(OrderStatus status);
    Page<Order> findAllByStatus(OrderStatus status, Pageable pageable);
    List<Order> findAllByAddressId(Long id);

    @Query("SELECT o FROM Order o JOIN FETCH o.user JOIN FETCH o.address")
    List<Order> findAllOrders();
}
