ALTER TABLE rent.ordr ADD user_id INT NOT NULL;
ALTER TABLE rent.ordr ADD CONSTRAINT users_orders_fk FOREIGN KEY (user_id) REFERENCES rent.user(id);