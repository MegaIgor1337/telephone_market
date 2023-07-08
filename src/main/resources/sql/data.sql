-- Вставка данных в таблицу "users"
INSERT INTO users (username, password, passport_no, email, role, gender, balance)
VALUES
    ('Igor', '{noop}FjfjfsjdlJJJJ32', 'JJ321444', 'tasfs@mail.ru', 'USER', 'MALE', 0),
    ('Maksim321', '{noop}1234567', 'FF432444', 'Jksjds@mail.ru', 'USER', 'MALE', 0),
    ('Gennadiy22', '{noop}boring', 'LL324455', 'fkdkf@mail.ru', 'ADMIN', 'MALE', 0),
    ('Georgiy', '{noop}borirstweng', 'LL324355', 'fkddfgfdh@mail.ru', 'ADMIN', 'MALE', 0);

-- Вставка данных в таблицу "addresses"
INSERT INTO addresses (country, city, street, house, flat, user_id)
VALUES
    ('Belarus', 'Grodno', 'Oginskogo', '4a', '14', 1),
    ('Russia', 'Moscow', 'Arbat', '42', '12', 2);

-- Вставка данных в таблицу "brands"
INSERT INTO brands (name)
VALUES
    ('Xiaomi'),
    ('Samsung'),
    ('Nokia'),
    ('Google Pixel'),
    ('Apple');

-- Вставка данных в таблицу "models"
INSERT INTO models (name)
VALUES
    ('12 Pro'),
    ('Galaxy S5'),
    ('POCA X3'),
    ('L4500'),
    ('5L');

-- Вставка данных в таблицу "colors"
INSERT INTO colors (color)
VALUES
    ('Black'),
    ('Yellow'),
    ('Pink'),
    ('Gray');

-- Вставка данных в таблицу "countries"
INSERT INTO countries (country)
VALUES
    ('Russia'),
    ('Germany'),
    ('USA');

-- Вставка данных в таблицу "comments"
INSERT INTO comments (comment, user_id, status)
VALUES
    ('Nice', 1, 'MODERATING'),
    ('Good', 1, 'ACCESSED'),
    ('Perfect', 2, 'MODERATING'),
    ('Lalal', 2, 'ACCESSED');

-- Вставка данных в таблицу "products"
INSERT INTO products (brand_id, model_id, color_id, country_id, storage_count, cost)
VALUES
    (2, 1, 3, 1, 14, 250),
    (1, 2, 1, 2, 10, 150),
    (3, 3, 3, 3, 20, 350),
    (4, 4, 2, 2, 40, 300),
    (5, 5, 1, 3, 70, 320);

-- Вставка данных в таблицу "orders"
INSERT INTO orders (user_id, cost, date, status)
VALUES
    (1, 300, NOW(), 'DELIVERED'),
    (2, 750, NOW(), 'WAITING_PAID');

-- Вставка данных в таблицу "order_products"
INSERT INTO order_products (order_id, product_id, user_count)
VALUES
    (1, 4, 2),
    (1, 3, 5),
    (2, 2, 5);

-- Вставка данных в таблицу "promo_codes"
INSERT INTO promo_codes (discount, code, status)
VALUES
    (3.0, 'JJDKE44', 'ACTIVE'),
    (10.0, 'FIRST', 'ACTIVE');

-- Вставка данных в таблицу "promo_code_products"
INSERT INTO promo_code_products (product_id, promo_code_id, start_date, end_date)
VALUES
    (1, 1, NOW(), NULL),
    (2, 1, NOW(), NULL),
    (3, 1, NOW(), NULL),
    (1, 2, NOW(), NULL),
    (2, 2, NOW(), NULL),
    (3, 2, NOW(), NULL),
    (4, 2, NOW(), NULL),
    (5, 2, NOW(), NULL);

-- Вставка данных в таблицу "favourites"
INSERT INTO favourites (product_id, user_id, date_added)
VALUES
    (2, 1, NOW()),
    (3, 2, NOW());
