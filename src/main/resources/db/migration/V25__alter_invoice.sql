ALTER TABLE rent.invoice DROP FOREIGN KEY users_invoices_fk;
ALTER TABLE rent.invoice DROP INDEX  users_invoices_fk;
ALTER TABLE rent.invoice DROP COLUMN user_id;