-- Заполнение таблицы "users"
INSERT INTO users (username, password, passport_no, email, role, gender, balance)
VALUES
    ('Igor', '{noop}FjfjfsjdlJJJJ32', 'JJ321444', 'tasfs@mail.ru', 'USER', 'MALE', 0.00),
    ('Maksim321', '{noop}1234567', 'FF432444', 'Jksjds@mail.ru', 'USER', 'MALE', 0.00),
    ('Gennadiy22', '{noop}boring', 'LL324455', 'fkdkf@mail.ru', 'ADMIN', 'MALE', 0.00),
    ('Georgiy', '{noop}borirstweng', 'LL324355', 'fkddfgfdh@mail.ru', 'ADMIN', 'MALE', 0.00);

-- Заполнение таблицы "addresses"
INSERT INTO address (country, city, street, house, flat, user_id)
VALUES
    ('Belarus', 'Grodno', 'Oginskogo', '4a', '14', 1),
    ('Russia', 'Moscow', 'Arbat', '42', '12', 2);

-- Заполнение таблицы "brands"
INSERT INTO brand (brand)
VALUES
    ('Xiaomi'),
    ('Samsung'),
    ('Nokia'),
    ('Google Pixel'),
    ('Apple');

-- Заполнение таблицы "models"
INSERT INTO model (model)
VALUES
    ('12 Pro'),
    ('Galaxy S5'),
    ('POCA X3'),
    ('L4500'),
    ('5L');

-- Заполнение таблицы "colors"
INSERT INTO color (color)
VALUES
    ('Black'),
    ('Yellow'),
    ('Pink'),
    ('Gray');

-- Заполнение таблицы "countries"
INSERT INTO country (country)
VALUES
    ('Russia'),
    ('Germany'),
    ('USA');

-- Заполнение таблицы "comments"
INSERT INTO comments (comment, user_id, status)
VALUES
    ('Nice', 1, 'MODERATING'),
    ('Good', 1, 'ACCESSED'),
    ('Perfect', 2, 'MODERATING'),
    ('Lalal', 2, 'ACCESSED');

-- Заполнение таблицы "products"
INSERT INTO product (brand_id, model_id, color_id, country_id, count_on_storage, cost)
VALUES
    (1, 2, 3, 1, 14, 250.00),
    (2, 1, 1, 2, 10, 150.00),
    (3, 3, 3, 3, 20, 350.00),
    (4, 4, 2, 2, 40, 300.00),
    (5, 5, 1, 3, 70, 320.00);

-- Заполнение таблицы "orders"
INSERT INTO "order" (user_id, cost, date, status, date_of_delivery)
VALUES
    (1, 300.00, NOW(), 'DELIVERED', NOW()),
    (2, 750.00, NOW(), 'WAITING_PAID', NOW());

-- Заполнение таблицы "order_products"
INSERT INTO order_product (order_id, product_id, user_count)
VALUES
    (1, 4, 2),
    (1, 3, 5),
    (2, 2, 5);

-- Заполнение таблицы "promo_codes"
INSERT INTO promo_code (discount, name, status)
VALUES
    (3.0, 'JJDKE44', 'ACTIVE'),
    (10.0, 'FIRST', 'ACTIVE');

-- Заполнение таблицы "promo_code_products"
INSERT INTO promo_code_product (product_id, promo_code_id, date_of_begin, date_of_end)
VALUES
    (1, 1, NOW(), NULL),
    (2, 1, NOW(), NULL),
    (3, 1, NOW(), NULL),
    (1, 2, NOW(), NULL),
    (2, 2, NOW(), NULL),
    (3, 2, NOW(), NULL),
    (4, 2, NOW(), NULL),
    (5, 2, NOW(), NULL);

-- Заполнение таблицы "favourites"
INSERT INTO favourite (product_id, user_id, date)
VALUES
    (2, 1, NOW()),
    (3, 2, NOW());
