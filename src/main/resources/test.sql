

CREATE TABLE user_roles (
                            role_id SMALLINT NOT NULL KEY,
                            role_description VARCHAR(32) UNIQUE
) ;

CREATE TABLE invoice_status (
                                status_id SMALLINT NOT NULL KEY,
                                status_description VARCHAR(32) UNIQUE
) ;

CREATE TABLE products (
                          product_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
                          product_code VARCHAR(12) NOT NULL UNIQUE,
                          product_name VARCHAR(128),
                          product_description VARCHAR(255),
                          product_cost DOUBLE NOT NULL DEFAULT 0,
                          product_quantity DOUBLE NOT NULL DEFAULT 0
) ;

CREATE TABLE users (
                       user_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
                       user_login VARCHAR(128) UNIQUE,
                       user_password VARCHAR(128),
                       user_name VARCHAR(128),
                       user_surname VARCHAR(128),
                       role_id SMALLINT NOT NULL DEFAULT 0,
                       FOREIGN KEY (role_id) REFERENCES user_roles (role_id)
) ;

CREATE TABLE invoices (
                          invoice_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
                          invoice_code BIGINT UNIQUE NOT NULL,
                          user_id SMALLINT,
                          status_id SMALLINT NOT NULL,
                          invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          invoice_notes VARCHAR(255),
                          FOREIGN KEY (user_id) REFERENCES users (user_id)
                              ON UPDATE CASCADE
                              ON DELETE SET NULL,
                          FOREIGN KEY (status_id) REFERENCES invoice_status(status_id)
) ;

CREATE TABLE orders (
                        order_id SMALLINT NOT NULL AUTO_INCREMENT KEY,
                        invoice_code BIGINT NOT NULL,
                        product_code VARCHAR(12) NOT NULL,
                        order_quantity DOUBLE NOT NULL DEFAULT 0,
                        order_value DOUBLE NOT NULL DEFAULT 0,
                        FOREIGN KEY (product_code) REFERENCES products(product_code)
                            ON UPDATE CASCADE
                            ON DELETE RESTRICT,
                        FOREIGN KEY (invoice_code) REFERENCES invoices(invoice_code)
                            ON UPDATE CASCADE
                            ON DELETE CASCADE
) ;

