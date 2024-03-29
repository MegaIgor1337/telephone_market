package market.service.impl;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import market.exception.LackOfMoneyException;
import market.exception.ValidationException;
import market.model.entity.Order;
import market.model.entity.OrderProduct;
import market.model.entity.Product;
import market.model.entity.PromoCodeProduct;
import market.model.enums.OrderStatus;
import market.model.repository.*;
import market.service.EmailService;
import market.service.OrderService;
import market.service.dto.*;
import market.service.mapper.OrderDtoWithPageMapper;
import market.service.mapper.OrderMapper;
import market.service.mapper.UserMapper;
import market.service.util.GeneratePdfFile;
import market.service.util.PageUtil;
import market.service.validator.LackOfMoneyValidator;
import market.service.validator.ModerateOrderDtoValidator;
import market.service.validator.PromoCodeValidator;
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

import static market.service.util.ConstantContainer.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final AddressRepository addressRepository;
    private final EmailService emailService;
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

    @Override
    @Transactional
    public void addProductToBasket(Long userId, String productId, String count) {
        var product = productRepository.findById(Long.valueOf(productId));
        var order = orderRepository.findAllByUserIdAndStatus(userId, OrderStatus.WAITING_PAID);
        int productCount = count != null && !count.isBlank() ? Integer.parseInt(count) : 1;
        product.ifPresent(p -> p.setStorageCount(p.getStorageCount() - productCount));
        product.ifPresent(productRepository::saveAndFlush);
        addProductToCreatedBasket(order, product, productCount, productId);
        addProductToNewBasket(order, product, userId, productCount, productId);
    }


    private void addProductToNewBasket(Optional<Order> order, Optional<Product> product,
                                       Long userId, Integer productCount,
                                       String productId) {
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
            log.info("Product {} added to basket, that was created at right now", productId);
        }
    }

    private void addProductToCreatedBasket(Optional<Order> order, Optional<Product> product,
                                           Integer productCount, String productId) {
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
            log.info("Product {} added to basket, that has created late", productId);
        }
    }

    @Override
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
                .orderDtoToOrderDtoWithPage(orderDto, page, PAGE_SIZE);
        return Optional.of(orderDtoWithPage);
    }

    @Override
    public OrderDtoWithPage getOrderDtoWithPage(OrderDto orderDto, Integer page) {
        return orderDtoWithPageMapper.orderDtoToOrderDtoWithPage(orderDto, page, PAGE_SIZE);
    }

    @Override
    @Transactional
    public void deleteProductFromBasket(Long productId, Long userId) {
        var order = orderRepository.findAllByUserIdAndStatus(userId, OrderStatus.WAITING_PAID);
        var product = productRepository.findById(productId);
        if (product.isPresent() && order.isPresent()) {
            var orderProduct = orderProductRepository
                    .findOrderProductByOrderAndProduct(order.get(), product.get());
            Integer userCount = orderProduct.isPresent() ? orderProduct.get().getUserCount() : 0;
            if (userCount == 0) {
                var productCount = product.get().getStorageCount();
                BigDecimal orderCost = order.get().getCost();
                order.get().setCost(orderCost.subtract(product.get().getCost().multiply(new BigDecimal(userCount))));
                product.get().setStorageCount(productCount + userCount);
            }
            orderRepository.saveAndFlush(order.get());
            productRepository.saveAndFlush(product.get());
            orderProductRepository.deleteOrderProductByProductAndOrder(product.get(), order.get());
            log.info("Product {} deleted from basket", productId);
        }

    }

    @Override
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
        order.ifPresent(o -> {
            o.setStatus(OrderStatus.MODERATING);
            o.setAddress(address);
            o.setCost(orderDto.getCost());
        });
        order.ifPresent(orderRepository::saveAndFlush);
        var userEntity = userMapper.userDtotoUser(userDto);
        var userAddresses = addressRepository.findByUserId(userEntity.getId());
        userEntity.setAddresses(userAddresses);
        userRepository.saveAndFlush(userEntity);
        log.info("User payed order, price - {}", orderDto.getCost());
    }

    @Override
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

    @Override
    public Page<OrderDto> findAllOrdersByUserID(Long userId, Integer page) {
        var orders = orderRepository.findAllByUserId(userId);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(DATE).descending());
        var pageOrder = PageUtil.createPageFromList(orders, pageable);
        return pageOrder.map(orderMapper::orderToOrderDto);
    }

    @Override
    public OrderDtoWithPage findById(Long orderId, Integer page) {
        var order = orderRepository.findById(orderId);
        return orderDtoWithPageMapper.orderDtoToOrderDtoWithPage(orderMapper
                .orderToOrderDto(order
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND))), page, PAGE_SIZE);
    }

    @Override
    public Integer getSizeModerateOrders() {
        return orderRepository.findAllByStatus(OrderStatus.MODERATING).size();
    }


    @Override
    public Page<OrderDto> getOrdersByStatus(OrderStatus orderStatus, Integer page) {
        return orderRepository.findAllByStatus(orderStatus, PageRequest.of(page, PAGE_SIZE))
                .map(orderMapper::orderToOrderDto);
    }

    @Override
    @Transactional
    public void moderateOrder(Long id, ModerateOrderDto moderateOrderDto) throws DocumentException {
        var validationResult = moderateOrderDtoValidator.isValid(moderateOrderDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        order.setStatus(moderateOrderDto.getStatus());
        order.setAddress(addressRepository.findById(moderateOrderDto.getDeliveryAddress()).orElseThrow());
        if (order.getStatus().equals(OrderStatus.DELIVER_PROCESSING)) {
            order.setDateOfDelivery(moderateOrderDto.getDateOfDelivery());
        }
        if (order.getStatus().equals(OrderStatus.CANCEL)) {
            canceledOrder(order.getId());
        }
        orderRepository.saveAndFlush(order);
        emailService.sendPdfFileOnEmail(order.getUser().getEmail(), GeneratePdfFile.generatePdf(order));
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

    @Override
    public Page<OrderDto> getAllOrders(Integer page, OrderFilterDto orderFilterDto) {
        var specifications = getSpecifications(orderFilterDto);
        return orderRepository.findAll(specifications, PageRequest.of(page, PAGE_SIZE))
                .map(orderMapper::orderToOrderDto);
    }


    private Specification<Order> getSpecifications (OrderFilterDto orderFilterDto) {
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
