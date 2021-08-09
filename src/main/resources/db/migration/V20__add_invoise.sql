CREATE TABLE invoise
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    serial_number INT NOT NULL,
    message VARCHAR(70),
    total DECIMAL(10,2) NOT NULL,
    CONSTRAINT order_id_fk FOREIGN KEY (order_id) REFERENCES rent.ordr (id) ON DELETE RESTRICT
);