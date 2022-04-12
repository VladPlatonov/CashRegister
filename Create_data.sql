INSERT INTO invoice_status (status_id, status_description) VALUES (0, 'CREATED');
INSERT INTO invoice_status (status_id, status_description) VALUES (1, 'FINISHED');
INSERT INTO invoice_status (status_id, status_description) VALUES (2, 'CANCELLED');

INSERT INTO user_roles (role_id, role_description) VALUES (0, 'USER');
INSERT INTO user_roles (role_id, role_description) VALUES (1, 'CASHIER');
INSERT INTO user_roles (role_id, role_description) VALUES (2, 'SENIOR_CASHIER');
INSERT INTO user_roles (role_id, role_description) VALUES (3, 'MERCHANT');
INSERT INTO user_roles (role_id, role_description) VALUES (4, 'ADMIN');

INSERT INTO users (user_login, user_password, user_name,user_surname,  role_id)
VALUES ('Guest', 'guest', 'test','test', 0);
INSERT INTO users (user_login, user_password, user_name,user_surname,  role_id)
VALUES ('Cashier', 'cashier', 'Cashier', 'Cashier', 1);
INSERT INTO users (user_login, user_password, user_name,user_surname,  role_id)
VALUES ('Senior_cashier', 'senior_cashier', 'Senior_cashier', 'Senior_cashier',  2);
INSERT INTO users (user_login, user_password, user_name,user_surname,  role_id)
VALUES ('Merchant', 'merchant', 'Merchant','Merchant', 3);
INSERT INTO users (user_login, user_password, user_name,user_surname,  role_id)
VALUES ('Admin', 'admin', 'Admin','Admin', 4);

INSERT INTO products(product_code, product_name, product_description,product_cost,product_quantity)
VALUES ('DE01','Coca Cola','Coca Cola 0.5l',10,92),
       ('DE02','Sprite','Sprite 0.5l',10,97),
       ('DE03','Fanta','Fanta 0.5l',10,98),
       ('DE04','Pepsi','Pepsi 0.5l',10,100),
       ('DE05','Coca Cola','Coca Cola 1l',10,111);