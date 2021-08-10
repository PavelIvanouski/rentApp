ALTER TABLE rent.invoice ADD user_id INT NOT NULL;
ALTER TABLE rent.invoice ADD CONSTRAINT users_invoices_fk FOREIGN KEY (user_id) REFERENCES rent.user(id);