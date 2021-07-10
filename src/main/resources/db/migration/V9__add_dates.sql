ALTER TABLE rent.car ADD COLUMN creating_date TIMESTAMP NOT NULL;
ALTER TABLE rent.car ADD COLUMN updating_date TIMESTAMP;
ALTER TABLE rent.car_model ADD COLUMN creating_date TIMESTAMP NOT NULL;
ALTER TABLE rent.car_model ADD COLUMN updating_date TIMESTAMP;
ALTER TABLE rent.ordr ADD COLUMN creating_date TIMESTAMP NOT NULL;
ALTER TABLE rent.ordr ADD COLUMN updating_date TIMESTAMP;
ALTER TABLE rent.user ADD COLUMN creating_date TIMESTAMP NOT NULL;
ALTER TABLE rent.user ADD COLUMN updating_date TIMESTAMP;