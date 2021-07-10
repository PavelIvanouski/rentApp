CREATE TABLE brand
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);
INSERT INTO brand VALUES (1, 'VW');
INSERT INTO brand VALUES (2, 'Audi');
INSERT INTO brand VALUES (3, 'BMW');

CREATE TABLE car_model
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    brand_id INT NOT NULL,
    CONSTRAINT brand_id_fk FOREIGN KEY (brand_id) REFERENCES brand (id) ON DELETE RESTRICT
);



CREATE TABLE engine
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);
INSERT INTO engine VALUES (1, 'Benzine');
INSERT INTO engine VALUES (2, 'Diesel');
INSERT INTO engine VALUES (3, 'Electric');

CREATE TABLE type
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);
INSERT INTO  type VALUES(1, 'Saloon');
INSERT INTO  type VALUES(2, 'Hatchback');
INSERT INTO  type VALUES(3, 'Estate');
INSERT INTO  type VALUES(4, 'Coupe');
INSERT INTO  type VALUES(5, 'Sedan');

CREATE TABLE car
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    model_id INT,
    engine_id INT NOT NULL,
    type_id INT NOT NULL,
    engine_volume int,
    seats_num INT COMMENT 'Number of seats' NOT NULL,
    auto_transmission BOOLEAN,
    vin CHAR (17) NOT NULL,
    state_num CHAR (10),
    year CHAR (4) COMMENT 'year of issue' NOT NULL,
    CONSTRAINT model_id_fk FOREIGN KEY (model_id) REFERENCES car_model (id) ON DELETE RESTRICT,
    CONSTRAINT engine_id_fk FOREIGN KEY (engine_id) REFERENCES engine (id) ON DELETE RESTRICT,
    CONSTRAINT type_id_fk FOREIGN KEY (type_id) REFERENCES type (id) ON DELETE RESTRICT
);



CREATE TABLE role
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);
INSERT INTO role VALUES (1,'admin');
INSERT INTO role VALUES (2,'user');

CREATE TABLE customer
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR (30) NOT NULL,
    password VARCHAR (20) NOT NULL,
    first_name VARCHAR (40) NOT NULL,
    last_name VARCHAR (40) NOT NULL,
    passport CHAR (14) NOT NULL,
    active BOOLEAN COMMENT 'equals false for deleted customer'
);

CREATE TABLE customer_role
(
    customer_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (customer_id,role_id),
    CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE status
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (20) NOT NULL
);
INSERT INTO status VALUES (1,'invoised ');
INSERT INTO status VALUES (2,'booked');
INSERT INTO status VALUES (3,'closed');
INSERT INTO status VALUES (4,'denied');

CREATE TABLE rent
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    car_id INT NOT NULL,
    customer_id INT NOT NULL,
    status_id INT NOT NULL,
    rent_begin TIMESTAMP NOT NULL,
    rent_end TIMESTAMP NOT NULL,
    CONSTRAINT car_id_fk FOREIGN KEY (car_id) REFERENCES car (id) ON DELETE RESTRICT,
    CONSTRAINT customers_rents_fk FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE RESTRICT,
    CONSTRAINT status_id_fk FOREIGN KEY (status_id) REFERENCES status (id) ON DELETE RESTRICT
);

