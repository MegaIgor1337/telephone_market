package market.service;

import lombok.RequiredArgsConstructor;
import market.dto.OrderDto;
import market.dto.OrderDtoWithPage;
import market.dto.UserDto;
import market.entity.Order;
import market.entity.OrderProduct;
import market.entity.PromoCodeProduct;
import market.enums.OrderStatus;
import market.exception.LackOfMoneyException;
import market.exception.ValidationException;
import market.mapper.OrderDtoWithPageMapper;
import market.mapper.OrderMapper;
import market.mapper.UserMapper;
import market.repository.*;
import market.validator.LackOfMoneyValidator;
import market.validator.PromoCodeValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {
    private final Integer pageSize = 2;
    private final OrderMapper orderMapper;
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeValidator promoCodeValidator;
    private final OrderDtoWithPageMapper orderDtoWithPageMapper;
    private final UserMapper userMapper;
    private final PromoCodeProductRepository promoCodeProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final LackOfMoneyValidator lackOfMoneyValidator;

    @Transactional
    public void addProductToBasket(Long userId, String productId, String count) {
        var product = productRepository.findById(Long.valueOf(productId));
        var order = orderRepository.findAllByUserIdAndStatus(userId, OrderStatus.WAITING_PAID);
        int productCount = count != null && !count.isBlank() ? Integer.parseInt(count) : 1;
        product.ifPresent(p -> p.setStorageCount(p.getStorageCount() - productCount));
        product.ifPresent(productRepository::saveAndFlush);
        if (product.isPresent() && order.isPresent()) {
            BigDecimal presentCost = order.get().getCost();
            order.get().setCost(presentCost.add(product.get().getCost().multiply(new BigDecimal(productCount))));
            var orderProduct = OrderProduct.builder()
                    .product(product.get())
                    .order(order.get())
                    .userCount(productCount)
                    .build();
            orderRepository.saveAndFlush(order.get());
            orderProductRepository.save(orderProduct);
        }
        if (product.isPresent() && order.isEmpty()) {
            var newOrder = Order.builder()
                    .status(OrderStatus.WAITING_PAID)
                    .date(LocalDateTime.now())
                    .user(userRepository.findById(userId).orElse(null))
                    .cost(product.get().getCost().multiply(new BigDecimal(productCount)))
                    .build();
            var orderProduct = OrderProduct.builder()
                    .order(newOrder)
                    .product(product.get())
                    .userCount(productCount)
                    .build();
            orderRepository.saveAndFlush(newOrder);
            orderProductRepository.save(orderProduct);
        }
    }

    @Transactional
    public Optional<OrderDtoWithPage> findByUserIdActiveBasket(Long id, Integer page) {
        var order = orderRepository.findAllByUserIdAndStatus(id, OrderStatus.WAITING_PAID);
        if (order.isEmpty()) {
            var newOrder = Order.builder()
                    .user(userRepository.findById(id).orElse(null))
                    .date(LocalDateTime.now())
                    .status(OrderStatus.WAITING_PAID)
                    .cost(BigDecimal.ZERO)
                    .build();
            order = Optional.of(orderRepository.save(newOrder));
        }
        var orderDto = orderMapper.orderToOrderDto(order.get());
        OrderDtoWithPage orderDtoWithPage = orderDtoWithPageMapper
                .orderDtoToOrderDtoWithPage(orderDto, page, pageSize);
        return Optional.of(orderDtoWithPage);
    }

    public OrderDtoWithPage getOrderDtoWithPage(OrderDto orderDto, Integer page) {
        return orderDtoWithPageMapper.orderDtoToOrderDtoWithPage(orderDto, page, pageSize);
    }

    @Transactional
    public void deleteProductFromBasket(Long productId, Long userId) {
        var order = orderRepository.findAllByUserIdAndStatus(userId, OrderStatus.WAITING_PAID);
        var product = productRepository.findById(productId);
        if (product.isPresent() && order.isPresent()) {
            var orderProduct = orderProductRepository
                    .findOrderProductByOrderAndProduct(order.get(), product.get());
            Integer userCount = orderProduct.isPresent() ? orderProduct.get().getUserCount() : 0;
            var productCount = product.get().getStorageCount();
            BigDecimal orderCost = order.get().getCost();
            order.get().setCost(orderCost.subtract(product.get().getCost().multiply(new BigDecimal(userCount))));
            product.get().setStorageCount(productCount + userCount);
            orderRepository.saveAndFlush(order.get());
            productRepository.saveAndFlush(product.get());
            orderProductRepository.deleteOrderProductByProductAndOrder(product.get(), order.get());
        }

    }

    @Transactional
    public void payOrder(UserDto userDto, OrderDtoWithPage orderDto) {
        lackOfMoneyValidator.putOrderCost(orderDto.getCost());
        var moneyResult = lackOfMoneyValidator.isValid(userDto);
        if (!moneyResult.isValid()) throw new LackOfMoneyException(moneyResult.getErrors());
        var userBalance = userDto.getBalance();
        userDto.setBalance(userBalance.subtract(orderDto.getCost()));
        var order = orderRepository.findAllByUserIdAndStatus(userDto.getId(), OrderStatus.WAITING_PAID);
        order.ifPresent(o -> o.setStatus(OrderStatus.MODERATING));
        order.ifPresent(orderRepository::saveAndFlush);
        userRepository.saveAndFlush(userMapper.userDtotoUser(userDto));
    }

    public OrderDto acceptPromoCode(String promoCode, Long userId) {
        promoCodeValidator.putPromCodes(promoCodeRepository.findAll());
        promoCodeValidator.putOrders(orderRepository.findByUserId(userId));
        var promoCodeResult = promoCodeValidator.isValid(promoCode);
        if (!promoCodeResult.isValid()) throw new ValidationException(promoCodeResult.getErrors());
        var orderOptional = orderRepository.findAllByUserIdAndStatus(userId, OrderStatus.WAITING_PAID);
        Order order = orderOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var promoCodeObject = promoCodeRepository.findAllByName(promoCode);
        var discount = promoCodeObject
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getDiscount();
        var promoCodeProducts = promoCodeProductRepository.findAllByPromoCode(promoCodeObject.get());
        var discountProducts = promoCodeProducts.stream().map(PromoCodeProduct::getProduct).toList();
        var orderProducts = orderProductRepository.findAllByOrderId(order.getId());
        var discountOrderProducts = orderProducts.stream().map(p -> {
            if (discountProducts.contains(p.getProduct()))
                p.getProduct().setCost(p.getProduct().getCost().multiply((BigDecimal
                        .valueOf(100 - discount).divide(BigDecimal.valueOf(100)))));
            return p;
        }).toList();
        order.setCost(findCost(discountOrderProducts));
        order.setProducts(discountOrderProducts);
        return orderMapper.orderToOrderDto(order);
    }

    private BigDecimal findCost(List<OrderProduct> orderProducts) {
        var iterator = orderProducts.iterator();
        BigDecimal sum = BigDecimal.ZERO;
        while (iterator.hasNext()) {
            var product = iterator.next();
            sum = sum.add((product.getProduct().getCost())
                    .multiply(BigDecimal.valueOf(product.getUserCount())));
        }
        return sum;
    }
}
