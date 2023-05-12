package utils;

import entity.*;
import entity.enums.CommentStatus;
import entity.enums.Gender;
import entity.enums.PromoCodeStatus;
import entity.enums.Role;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@UtilityClass

public class TestDataImporter {
    public void importData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            User user1 = saveUser(session, "Igor", "FjfjfsjdlJJJJ32",
                    "JJ321444", "tasfs@mail.ru", Role.USER, Gender.MALE, BigDecimal.valueOf(0));
            User user2 = saveUser(session, "Maksim321", "1234567", "FF432444",
                    "Jksjds@mail.ru", Role.USER, Gender.MALE, BigDecimal.valueOf(0));
            User user3 = saveUser(session, "Gennadiy22", "boring", "LL324455",
                    "fkdkf@mail.ru", Role.ADMIN, Gender.MALE, BigDecimal.valueOf(0));
            User user4 = saveUser(session, "Georgiy", "borirstweng", "LL324355",
                    "fkddfgfdh@mail.ru", Role.ADMIN, Gender.MALE, BigDecimal.valueOf(0));


            Address address1 = saveAddress(session, "Belarus", "Grodno", "Oginskogo",
                    "4a", "14", user1);
            Address address2 = saveAddress(session, "Russia", "Moscow", "Arbat",
                    "42", "12", user2);

            Brand brand1 = saveBrand(session, "Xiaomi");
            Brand brand2 = saveBrand(session, "Samsung");
            Brand brand3 = saveBrand(session, "Nokia");
            Brand brand4 = saveBrand(session, "Google Pixel");
            Brand brand5 = saveBrand(session, "Apple");

            Model model1 = saveModel(session, "12 Pro");
            Model model2 = saveModel(session, "Galaxy S5");
            Model model3 = saveModel(session, "POCA X3");
            Model model4 = saveModel(session, "L4500");
            Model model5 = saveModel(session, "5L");

            Color color1 = saveColor(session, "Black");
            Color color2 = saveColor(session, "Yellow");
            Color color3 = saveColor(session, "Pink");
            Color color4 = saveColor(session, "Gray");

            Country country1 = saveCountry(session, "Russia");
            Country country2 = saveCountry(session, "Germany");
            Country country3 = saveCountry(session, "USA");

            Comment comment1 = saveComment(session, "Nice", user1, CommentStatus.MODERATING);
            Comment comment2 = saveComment(session, "Good", user1, CommentStatus.ACCESSED);
            Comment comment3 = saveComment(session, "Perfect", user2, CommentStatus.MODERATING);
            Comment comment4 = saveComment(session, "Lalal", user2, CommentStatus.ACCESSED);

            Product product1 = saveProduct(session, brand1, model2, color3, country1, 14, BigDecimal.valueOf(250));
            Product product2 = saveProduct(session, brand2, model1, color1, country2, 10, BigDecimal.valueOf(150));
            Product product3 = saveProduct(session, brand3, model3, color3, country3, 20, BigDecimal.valueOf(350));
            Product product4 = saveProduct(session, brand4, model4, color2, country2, 40, BigDecimal.valueOf(300));
            Product product5 = saveProduct(session, brand5, model5, color1, country3, 70, BigDecimal.valueOf(320));

            Order order1 = saveOrder(session, user1, BigDecimal.valueOf(300), LocalDateTime.now(),
                    false, LocalDateTime.now());
            Order order2 = saveOrder(session, user2, BigDecimal.valueOf(500), LocalDateTime.now(),
                    false, LocalDateTime.now());

            OrderProduct orderProduct1 = saveOrderProduct(session, order1, product4, 2);
            OrderProduct orderProduct2 = saveOrderProduct(session, order1, product3, 5);
            OrderProduct orderProduct3 = saveOrderProduct(session, order2, product2, 5);

            PromoCode promoCode1 = savePromoCode(session, 3.0, "JJDKE44", PromoCodeStatus.ACTIVE);
            PromoCode promoCode2 = savePromoCode(session, 10.0, "FIRST", PromoCodeStatus.ACTIVE);

            PromoCodeProduct promoCodeProduct1 = savePromoCodeProduct(session, product1, promoCode1,
                    LocalDateTime.now(), null);
            PromoCodeProduct promoCodeProduct2 = savePromoCodeProduct(session, product2, promoCode1,
                    LocalDateTime.now(), null);
        }
    }

    private User saveUser(Session session, String name, String password, String passportNo,
                          String email, Role role, Gender gender, BigDecimal balance) {
        User user = User.builder()
                .name(name)
                .password(password)
                .passportNo(passportNo)
                .email(email)
                .role(role)
                .gender(gender)
                .balance(balance)
                .build();
        session.save(user);
        return user;
    }


    private Address saveAddress(Session session, String country, String city,
                                String street, String house, String flat, User user) {
        Address address = Address.builder()
                .country(country)
                .city(city)
                .house(house)
                .street(street)
                .flat(flat)
                .user(user)
                .build();
        session.save(address);
        return address;
    }

    private Brand saveBrand(Session session, String name) {
        Brand brand = Brand.builder()
                .brand(name)
                .build();
        session.save(brand);
        return brand;
    }

    private Color saveColor(Session session, String name) {
        Color color = Color.builder()
                .color(name)
                .build();
        session.save(color);
        return color;
    }

    private Model saveModel(Session session, String name) {
        Model model = Model.builder()
                .model(name)
                .build();
        session.save(model);
        return model;
    }

    private Country saveCountry(Session session, String name) {
        Country country = Country.builder()
                .name(name)
                .build();
        session.save(country);
        return country;
    }

    private Comment saveComment(Session session, String comment, User user, CommentStatus status) {
        Comment comment1 = Comment.builder()
                .comment(comment)
                .user(user)
                .status(status)
                .build();
        session.save(comment1);
        return comment1;
    }

    private Product saveProduct(Session session, Brand brand, Model model, Color color,
                                Country country, Integer count, BigDecimal cost) {
        Product product = Product.builder()
                .brand(brand)
                .model(model)
                .color(color)
                .country(country)
                .storageCount(count)
                .cost(cost)
                .build();
        session.save(product);
        return product;
    }

    private Order saveOrder(Session session, User user, BigDecimal cost, LocalDateTime date,
                            Boolean delivered, LocalDateTime dateOfDelivery) {
        Order order = Order.builder()
                .user(user)
                .cost(cost)
                .date(date)
                .delivered(delivered)
                .dateOfDelivery(dateOfDelivery)
                .build();
        session.save(order);
        return order;
    }

    private OrderProduct saveOrderProduct(Session session, Order order, Product product, Integer count) {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(order)
                .product(product)
                .clientCount(count)
                .build();
        session.save(orderProduct);
        return orderProduct;
    }

    private PromoCode savePromoCode(Session session, Double discount, String name, PromoCodeStatus status) {
        PromoCode promoCode = PromoCode.builder()
                .discount(discount)
                .name(name)
                .status(status)
                .build();
        session.save(promoCode);
        return promoCode;
    }

    private PromoCodeProduct savePromoCodeProduct(Session session, Product product, PromoCode promoCode,
                                                  LocalDateTime dateOfBegin, LocalDateTime dateOfEnd) {
        PromoCodeProduct promoCodeProduct = PromoCodeProduct.builder()
                .product(product)
                .promoCode(promoCode)
                .dateOfBegin(dateOfBegin)
                .dateOfEnd(dateOfEnd)
                .build();
        session.save(promoCodeProduct);
        return promoCodeProduct;
    }

}
