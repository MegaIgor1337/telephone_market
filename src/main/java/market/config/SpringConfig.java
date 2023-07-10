package market.config;

import lombok.RequiredArgsConstructor;
import market.entity.*;
import market.enums.*;
import market.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@ComponentScan(value = {"market.repository"})
@RequiredArgsConstructor
public class SpringConfig {
/*    @Bean
    public CommandLineRunner dataLoad(
            @Autowired UserRepository userRepository,
            @Autowired AddressRepository addressRepository,
            @Autowired BrandRepository brandRepository,
            @Autowired ColorRepository colorRepository,
            @Autowired CommentRepository commentRepository,
            @Autowired CountryRepository countryRepository,
            @Autowired FavouriteRepository favouriteRepository,
            @Autowired ModelRepository modelRepository,
            @Autowired OrderRepository orderRepository,
            @Autowired OrderProductRepository orderProductRepository,
            @Autowired ProductRepository productRepository,
            @Autowired PromoCodeRepository promoCodeRepository,
            @Autowired PromoCodeProductRepository promoCodeProductRepository) {
        return args -> {
            User user1 = saveUser(userRepository, "Igor", "{noop}FjfjfsjdlJJJJ32",
                    "JJ321444", "tasfs@mail.ru", Role.USER, Gender.MALE, BigDecimal.valueOf(0));
            User user2 = saveUser(userRepository, "Maksim321", "{noop}1234567", "FF432444",
                    "Jksjds@mail.ru", Role.USER, Gender.MALE, BigDecimal.valueOf(0));
            User user3 = saveUser(userRepository, "Gennadiy22", "{noop}boring", "LL324455",
                    "fkdkf@mail.ru", Role.ADMIN, Gender.MALE, BigDecimal.valueOf(0));
            User user4 = saveUser(userRepository, "Georgiy", "{noop}borirstweng", "LL324355",
                    "fkddfgfdh@mail.ru", Role.ADMIN, Gender.MALE, BigDecimal.valueOf(0));


            Address address1 = saveAddress(addressRepository, "Belarus", "Grodno", "Oginskogo",
                    "4a", "14", user1);
            Address address2 = saveAddress(addressRepository, "Russia", "Moscow", "Arbat",
                    "42", "12", user2);

            Brand brand1 = saveBrand(brandRepository, "Xiaomi");
            Brand brand2 = saveBrand(brandRepository, "Samsung");
            Brand brand3 = saveBrand(brandRepository, "Nokia");
            Brand brand4 = saveBrand(brandRepository, "Google Pixel");
            Brand brand5 = saveBrand(brandRepository, "Apple");

            Model model1 = saveModel(modelRepository, "12 Pro");
            Model model2 = saveModel(modelRepository, "Galaxy S5");
            Model model3 = saveModel(modelRepository, "POCA X3");
            Model model4 = saveModel(modelRepository, "L4500");
            Model model5 = saveModel(modelRepository, "5L");

            Color color1 = saveColor(colorRepository, "Black");
            Color color2 = saveColor(colorRepository, "Yellow");
            Color color3 = saveColor(colorRepository, "Pink");
            Color color4 = saveColor(colorRepository, "Gray");

            Country country1 = saveCountry(countryRepository, "Russia");
            Country country2 = saveCountry(countryRepository, "Germany");
            Country country3 = saveCountry(countryRepository, "USA");

            Comment comment1 = saveComment(commentRepository, "Nice", user1, CommentStatus.MODERATING);
            Comment comment2 = saveComment(commentRepository, "Good", user1, CommentStatus.ACCESSED);
            Comment comment3 = saveComment(commentRepository, "Perfect", user2, CommentStatus.MODERATING);
            Comment comment4 = saveComment(commentRepository, "Lalal", user2, CommentStatus.ACCESSED);

            Product product1 = saveProduct(productRepository, brand1, model2, color3, country1, 14, BigDecimal.valueOf(250));
            Product product2 = saveProduct(productRepository, brand2, model1, color1, country2, 10, BigDecimal.valueOf(150));
            Product product3 = saveProduct(productRepository, brand3, model3, color3, country3, 20, BigDecimal.valueOf(350));
            Product product4 = saveProduct(productRepository, brand4, model4, color2, country2, 40, BigDecimal.valueOf(300));
            Product product5 = saveProduct(productRepository, brand5, model5, color1, country3, 70, BigDecimal.valueOf(320));

            Order order1 = saveOrder(orderRepository, user1, BigDecimal.valueOf(300), LocalDateTime.now(),
                    OrderStatus.DELIVERED, LocalDate.now());
            Order order2 = saveOrder(orderRepository, user2, BigDecimal.valueOf(750), LocalDateTime.now(),
                    OrderStatus.WAITING_PAID, LocalDate.now());

            OrderProduct orderProduct1 = saveOrderProduct(orderProductRepository, order1, product4, 2);
            OrderProduct orderProduct2 = saveOrderProduct(orderProductRepository, order1, product3, 5);
            OrderProduct orderProduct3 = saveOrderProduct(orderProductRepository, order2, product2, 5);

            PromoCode promoCode1 = savePromoCode(promoCodeRepository, 3.0, "JJDKE44", PromoCodeStatus.ACTIVE);
            PromoCode promoCode2 = savePromoCode(promoCodeRepository, 10.0, "FIRST", PromoCodeStatus.ACTIVE);

            PromoCodeProduct promoCodeProduct1 = savePromoCodeProduct(promoCodeProductRepository, product1, promoCode1,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct2 = savePromoCodeProduct(promoCodeProductRepository, product2, promoCode1,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct3 = savePromoCodeProduct(promoCodeProductRepository, product3, promoCode1,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct4 = savePromoCodeProduct(promoCodeProductRepository, product1, promoCode2,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct5 = savePromoCodeProduct(promoCodeProductRepository, product2, promoCode2,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct6 = savePromoCodeProduct(promoCodeProductRepository, product3, promoCode2,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct7 = savePromoCodeProduct(promoCodeProductRepository, product4, promoCode2,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct8 = savePromoCodeProduct(promoCodeProductRepository, product5, promoCode2,
                    LocalDateTime.now(), null);

            Favourite favourite1 = saveFavourite(favouriteRepository, product2, user1, LocalDateTime.now());
            Favourite favourite2 = saveFavourite(favouriteRepository, product3, user2, LocalDateTime.now());
        };
    }

    private User saveUser(UserRepository userRepository, String name, String password, String passportNo,
                          String email, Role role, Gender gender, BigDecimal balance) {
        User user = User.builder()
                .username(name)
                .password(password)
                .passportNo(passportNo)
                .email(email)
                .role(role)
                .gender(gender)
                .balance(balance)
                .build();
        userRepository.save(user);
        return user;
    }


    private Address saveAddress(AddressRepository addressRepository, String country, String city,
                                String street, String house, String flat, User user) {
        Address address = Address.builder()
                .country(country)
                .city(city)
                .house(house)
                .street(street)
                .flat(flat)
                .user(user)
                .build();
        addressRepository.save(address);
        return address;
    }

    private Brand saveBrand(BrandRepository brandRepository, String name) {
        Brand brand = Brand.builder()
                .brand(name)
                .build();
        brandRepository.save(brand);
        return brand;
    }

    private Color saveColor(ColorRepository colorRepository, String name) {
        Color color = Color.builder()
                .color(name)
                .build();
        colorRepository.save(color);
        return color;
    }

    private Model saveModel(ModelRepository modelRepository, String name) {
        Model model = Model.builder()
                .model(name)
                .build();
        modelRepository.save(model);
        return model;
    }

    private Country saveCountry(CountryRepository countryRepository, String name) {
        Country country = Country.builder()
                .country(name)
                .build();
        countryRepository.save(country);
        return country;
    }

    private Comment saveComment(CommentRepository commentRepository, String comment, User user, CommentStatus status) {
        Comment comment1 = Comment.builder()
                .comment(comment)
                .user(user)
                .status(status)
                .build();
        commentRepository.save(comment1);
        return comment1;
    }

    private Product saveProduct(ProductRepository productRepository, Brand brand, Model model, Color color,
                                Country country, Integer count, BigDecimal cost) {
        Product product = Product.builder()
                .brand(brand)
                .model(model)
                .color(color)
                .country(country)
                .storageCount(count)
                .cost(cost)
                .build();
        productRepository.save(product);
        return product;
    }

    private Order saveOrder(OrderRepository orderRepository, User user, BigDecimal cost, LocalDateTime date,
                            OrderStatus orderStatus, LocalDate dateOfDelivery) {
        Order order = Order.builder()
                .user(user)
                .cost(cost)
                .date(date)
                .status(orderStatus)
                .dateOfDelivery(dateOfDelivery)
                .build();
        orderRepository.save(order);
        return order;
    }

    private OrderProduct saveOrderProduct(OrderProductRepository orderProductRepository, Order order, Product product, Integer count) {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .userCount(count)
                .build();
        orderProductRepository.save(orderProduct);
        return orderProduct;
    }

    private PromoCode savePromoCode(PromoCodeRepository promoCodeRepository, Double discount, String name, PromoCodeStatus status) {
        PromoCode promoCode = PromoCode.builder()
                .discount(discount)
                .name(name)
                .status(status)
                .build();
        promoCodeRepository.save(promoCode);
        return promoCode;
    }

    private PromoCodeProduct savePromoCodeProduct(PromoCodeProductRepository promoCodeProductRepository, Product product, PromoCode promoCode,
                                                  LocalDateTime dateOfBegin, LocalDateTime dateOfEnd) {
        PromoCodeProduct promoCodeProduct = PromoCodeProduct.builder()
                .product(product)
                .promoCode(promoCode)
                .dateOfBegin(dateOfBegin)
                .dateOfEnd(dateOfEnd)
                .build();
        promoCodeProductRepository.save(promoCodeProduct);
        return promoCodeProduct;
    }


    private Favourite saveFavourite(FavouriteRepository favouriteRepository, Product product, User user, LocalDateTime localDateTime) {
        Favourite favourite = Favourite.builder()
                .user(user)
                .product(product)
                .date(localDateTime)
                .build();
        favouriteRepository.save(favourite);
        return favourite;
    }*/
}
