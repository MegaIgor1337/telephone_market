package market.service;

import market.dto.*;
import market.entity.Order;
import market.entity.Product;
import market.enums.OrderStatus;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderService {
    void addProductToBasket(Long userId, String productId, String count);
    Optional<OrderDtoWithPage> findByUserIdActiveBasket(Long id, Integer page);
    OrderDtoWithPage getOrderDtoWithPage(OrderDto orderDto, Integer page);
    void deleteProductFromBasket(Long productId, Long userId);
    void payOrder(UserDto userDto, Long addressId, OrderDtoWithPage orderDto);
    OrderDto acceptPromoCode(String promoCode, Long userId);
    Page<OrderDto> findAllOrdersByUserID(Long userId, Integer page);
    OrderDtoWithPage findById(Long orderId, Integer page);
    Integer getSizeModerateOrders();
    Page<OrderDto> getOrdersByStatus(OrderStatus orderStatus, Integer page);
    void moderateOrder(Long id, ModerateOrderDto moderateOrderDto);
    Page<OrderDto> getAllOrders(Integer page, OrderFilterDto orderFilterDto);
}
