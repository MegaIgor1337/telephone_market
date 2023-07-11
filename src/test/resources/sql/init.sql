CREATE TABLE users (
                       id bigserial PRIMARY KEY ,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       passport_no VARCHAR(255),
                       email VARCHAR(255),
                       role VARCHAR(255),
                       gender VARCHAR(255),
                       balance DECIMAL(19, 2)
);



CREATE TABLE address (
                         id bigserial PRIMARY KEY ,
                         country VARCHAR(255) NOT NULL,
                         city VARCHAR(255) NOT NULL,
                         street VARCHAR(255) NOT NULL,
                         house VARCHAR(255) NOT NULL,
                         flat VARCHAR(255) ,
                         user_id BIGINT,
                         FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE brand (
                       id bigserial PRIMARY KEY,
                       brand VARCHAR(255)
);


CREATE TABLE color (
  id bigserial PRIMARY KEY ,
  color VARCHAR(255)
);

CREATE TABLE comments (
                          id bigserial PRIMARY KEY ,
                          comment VARCHAR(255),
                          status VARCHAR(255),
                          user_id bigint REFERENCES users (id)
);

CREATE TABLE country (
                         id BIGSERIAL PRIMARY KEY,
                         country VARCHAR(255)
);

CREATE TABLE model (
                       id BIGSERIAL PRIMARY KEY,
                       model VARCHAR(255)
);


CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         brand_id BIGINT,
                         model_id BIGINT,
                         color_id BIGINT,
                         country_id BIGINT,
                         count_on_storage INTEGER,
                         cost DECIMAL(19, 2),
                         FOREIGN KEY (brand_id) REFERENCES brand (id),
                         FOREIGN KEY (model_id) REFERENCES model (id),
                         FOREIGN KEY (color_id) REFERENCES color (id),
                         FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE TABLE favourite (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT,
                           product_id BIGINT,
                           date TIMESTAMP,
                           FOREIGN KEY (user_id) REFERENCES users (id),
                           FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE "order" (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT,
                         cost NUMERIC(19, 2),
                         date TIMESTAMP,
                         address_id BIGINT,
                         status VARCHAR(255),
                         date_of_delivery DATE,
                         FOREIGN KEY (user_id) REFERENCES users (id),
                         FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE order_product (
                               id BIGSERIAL PRIMARY KEY,
                               order_id BIGINT,
                               product_id BIGINT,
                               user_count INTEGER,
                               FOREIGN KEY (order_id) REFERENCES "order" (id),
                               FOREIGN KEY (product_id) REFERENCES product (id)
);
CREATE TABLE promo_code (
                            id BIGSERIAL PRIMARY KEY,
                            discount DOUBLE PRECISION,
                            name VARCHAR(255),
                            status VARCHAR(255)
);

CREATE TABLE promo_code_product (
                                    id BIGSERIAL PRIMARY KEY,
                                    promo_code_id BIGINT,
                                    product_id BIGINT,
                                    date_of_begin TIMESTAMP,
                                    date_of_end TIMESTAMP,
                                    FOREIGN KEY (promo_code_id) REFERENCES promo_code (id),
                                    FOREIGN KEY (product_id) REFERENCES product (id)
);



