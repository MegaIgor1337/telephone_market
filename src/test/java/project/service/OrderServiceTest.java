package project.service;

import static org.assertj.core.api.Assertions.assertThat;
import lombok.RequiredArgsConstructor;
import market.dto.ModerateOrderDto;
import market.dto.OrderFilterDto;
import market.enums.OrderStatus;
import market.exception.LackOfMoneyException;
import market.mapper.OrderMapper;
import market.mapper.UserMapper;
import market.repository.OrderRepository;
import market.repository.UserRepository;
import market.service.OrderService;
import market.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import project.annotation.IT;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@IT
@RequiredArgsConstructor
class OrderServiceTest {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    @Test
    void addProductToBasket() {
        orderService.addProductToBasket(2L, "2", "4");
        var result = orderRepository.findById(2L);
        assertThat(result.get().getProducts()).hasSize(2);
    }

    @Test
    void findByUserIdActiveBasket() {
        var result = orderService.findByUserIdActiveBasket(1L, 0);
        assertThat(result.get().getProducts()).hasSize(0);
    }



    @Test
    void deleteProductFromBasket() {
        orderService.deleteProductFromBasket(2L,2L);
        assertThat(orderRepository.findAllByUserIdAndStatus(2L, OrderStatus.WAITING_PAID).get()
                .getProducts()).hasSize(1);
    }

    @Test
    void payOrder() {
        var orderDtoWithPage = orderService.getOrderDtoWithPage(orderRepository
                .findById(2L).map(orderMapper::orderToOrderDto).get(), 0);
        try {
            orderService.payOrder(userRepository.findById(2L)
                    .map(userMapper::userToUserDto).get(), 2L, orderDtoWithPage);
            fail();
        } catch (LackOfMoneyException e) {
            assertThat(true).isTrue();
        }
    }


    @Test
    void acceptPromoCode() {
        orderService.acceptPromoCode("FIRST", 2L);
        var result = orderService.findById(2L, 0);
        assertThat(result.getCost().toString()).isEqualTo(BigDecimal.valueOf(675) + ".000");
    }

    @Test
    void findAllOrdersByUserID() {
        var result = orderService.findAllOrdersByUserID(2L, 0);
        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void findById() {
        var result = orderService.findById(2L, 0);
        assertThat(result.getProducts()).hasSize(1);
    }

    @Test
    void getSizeModerateOrders() {
        var result = orderService.getSizeModerateOrders();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void getOrdersByStatus() {
        var result = orderService.getOrdersByStatus(OrderStatus.DELIVERED, 0);
        assertThat(result).hasSize(1);
    }

    @Test
    void moderateOrder() {
        var moderateOrderDto = ModerateOrderDto.builder()
                .status(OrderStatus.DELIVER_PROCESSING)
                .dateOfDelivery(LocalDate.now())
                .build();
        orderService.moderateOrder(2L, moderateOrderDto);
        var result = orderService.findById(2L, 0);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.DELIVER_PROCESSING);
    }


    @Test
    void getAllOrders() {
        var result = orderService.getAllOrders(0, OrderFilterDto.builder()
                .username("Maksim321").build());
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getStatus()).isEqualTo(OrderStatus.WAITING_PAID);
    }
}