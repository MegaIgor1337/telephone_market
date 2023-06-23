package market.repository;

import market.entity.Order;
import market.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findAllByUserIdAndStatus(Long userId, OrderStatus orderStatus);
    List<Order> findByUserId(Long userId);
    List<Order> findAllByUserId(Long userId);
}
