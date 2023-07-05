package market.service;

import lombok.RequiredArgsConstructor;
import market.dto.*;
import market.entity.Order;
import market.entity.OrderProduct;
import market.entity.PromoCodeProduct;
import market.entity.User;
import market.enums.OrderStatus;
import market.enums.Role;
import market.exception.LackOfMoneyException;
import market.exception.ValidationException;
import market.mapper.AddressMapper;
import market.mapper.OrderDtoWithPageMapper;
import market.mapper.OrderMapper;
import market.mapper.UserMapper;
import market.repository.*;
import market.util.PageUtil;
import market.validator.LackOfMoneyValidator;
import market.validator.ModerateOrderDtoValidator;
import market.validator.PromoCodeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static market.util.StringContainer.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {
    private final Integer pageSize = 2;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ModerateOrderDtoValidator moderateOrderDtoValidator;
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
    public void payOrder(UserDto userDto, Long addressId, OrderDtoWithPage orderDto) {
        lackOfMoneyValidator.putOrderCost(orderDto.getCost());
        var moneyResult = lackOfMoneyValidator.isValid(userDto);
        if (!moneyResult.isValid()) throw new LackOfMoneyException(moneyResult.getErrors());
        var address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        var userBalance = userDto.getBalance();
        userDto.setBalance(userBalance.subtract(orderDto.getCost()));
        var order = orderRepository.findAllByUserIdAndStatus(userDto.getId(), OrderStatus.WAITING_PAID);
        order.ifPresent(o -> {o.setStatus(OrderStatus.MODERATING);
            o.setAddress(address);
            o.setCost(orderDto.getCost());
        });
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
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        var promoCodeObject = promoCodeRepository.findAllByName(promoCode);
        var discount = promoCodeObject
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND)).getDiscount();
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

    public Page<OrderDto> findAllOrdersByUserID(Long userId, Integer page) {
        var orders = orderRepository.findAllByUserId(userId);
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(DATE).descending());
        var pageOrder = PageUtil.createPageFromList(orders, pageable);
        return pageOrder.map(orderMapper::orderToOrderDto);
    }

    public OrderDtoWithPage findOrderById(Long orderId, Integer page) {
        var order = orderRepository.findById(orderId);
        return orderDtoWithPageMapper.orderDtoToOrderDtoWithPage(orderMapper
                .orderToOrderDto(order
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))), page, pageSize);
    }

    public Integer getSizeModerateOrders() {
        return orderRepository.findAllByStatus(OrderStatus.MODERATING).size();
    }

    public Page<OrderDto> getOrdersByStatus(OrderStatus orderStatus, Integer page) {
        return orderRepository.findAllByStatus(orderStatus, PageRequest.of(page, pageSize))
                .map(orderMapper::orderToOrderDto);
    }

    @Transactional
    public void moderateOrder(Long id, ModerateOrderDto moderateOrderDto) {
        var validationResult = moderateOrderDtoValidator.isValid(moderateOrderDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        order.setStatus(moderateOrderDto.getStatus());
        if (order.getStatus().equals(OrderStatus.DELIVER_PROCESSING)) {
            order.setDateOfDelivery(moderateOrderDto.getDateOfDelivery());
        }
        if (order.getStatus().equals(OrderStatus.CANCEL)) {
            canceledOrder(order.getId());
        }
        orderRepository.saveAndFlush(order);
    }

    protected void canceledOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        var user = order.getUser();
        var userBalance = user.getBalance();
        user.setBalance(userBalance.add(order.getCost()));
        var orderProductsFromOrder = order.getProducts();
        var allOrderProducts = orderProductRepository.findAll();
        var moderatedProducts = allOrderProducts.stream().map(op -> {
            var opFromOrder = orderProductsFromOrder.stream()
                    .filter(o -> o.equals(op)).findFirst();
            if (opFromOrder.isPresent()) {
                var storageCount = op.getProduct().getStorageCount();
                op.getProduct().setStorageCount(storageCount + opFromOrder.get().getUserCount());
            }
            return op;
        }).toList();
        orderProductRepository.saveAll(moderatedProducts);
        orderRepository.flush();
    }

    public Page<OrderDto> getAllOrders(Integer page, OrderFilterDto orderFilterDto) {
        var specifications = getSpecifications(orderFilterDto);
        return orderRepository.findAll(specifications, PageRequest.of(page, pageSize))
                .map(orderMapper::orderToOrderDto);
    }

    private Specification<Order> getSpecifications(OrderFilterDto orderFilterDto) {
        Specification<Order> specification = Specification.where(null);
        if (orderFilterDto.getUsername() != null && !orderFilterDto.getUsername().isBlank()) {
            specification = specification
                    .and(((root, query, cb) -> cb
                            .equal(root.get(USER).get(USER_NAME), orderFilterDto.getUsername())));
        }
        if (orderFilterDto.getNumber() != null) {
            specification = specification
                    .and((root, query, cb) -> cb.equal(root.get(ID), orderFilterDto.getNumber()));
        }
        if (orderFilterDto.getStatus() != null) {
            specification = specification
                    .and(((root, query, cb) -> cb.equal(root.get(STATUS), orderFilterDto.getStatus())));
        }
        return specification;
    }
}
