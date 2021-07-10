CREATE TABLE user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id,role_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES rent.user (id) ON DELETE CASCADE,
    CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES rent.role (id) ON DELETE CASCADE
);