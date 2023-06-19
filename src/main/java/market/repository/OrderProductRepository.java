package market.repository;

import market.entity.Order;
import market.entity.OrderProduct;
import market.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    @Modifying
    void deleteOrderProductByProductAndOrder(Product product, Order order);
    Optional<OrderProduct> findOrderProductByOrderAndProduct(Order order, Product product);
    List<OrderProduct> findAllByOrderId(Long id);
}
