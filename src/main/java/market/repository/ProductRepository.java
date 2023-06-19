package market.repository;

import market.entity.Product;
import market.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static market.util.StringContainer.ID;
import static market.util.StringContainer.STATUS;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>,
        QuerydslPredicateExecutor<Product> {
    Page<Product> findAllBy(Pageable pageable);
    @Query("SELECT p FROM Product p " +
           "JOIN FETCH p.orders op " +
           "JOIN FETCH op.order o " +
           "WHERE o.status = :status " +
           "AND o.user.id = :id")
    List<Product> findByIdAndOrderStatus(@Param(ID) Long id, @Param(STATUS) OrderStatus status);

    @Query(value = "SELECT p FROM Product p " +
                   "JOIN FETCH p.favourites f " +
                   "where f.user.id = :id")
    List<Product> findAllByInFavouritesAndUserId(@Param(ID) Long id);


    @Query(value = "select p from Product p " +
                   "JOIN p.orders op " +
                   "JOIN op.order o " +
                   "where o.id = :orderId")
    List<Product> findAlByOrderId(Long orderId);


}
