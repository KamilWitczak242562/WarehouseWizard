create database warehouse;
use warehouse;
SET autocommit = 1;
create table users (
	id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE Product (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

CREATE TABLE Transaction (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    quantity INT NOT NULL,
    transaction_type ENUM('IN', 'OUT') NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

INSERT INTO Category (name, description) VALUES
                                             ('Electronics', 'Electronic devices and gadgets'),
                                             ('Clothing', 'Clothes and fashion accessories'),
                                             ('Home & Garden', 'Items for home and garden');

INSERT INTO Product (category_id, name, description, price, stock_quantity) VALUES
                                                                                (1, 'Smartphone', 'High-end smartphone with advanced features', 899.99, 50),
                                                                                (1, 'Laptop', 'Powerful laptop for productivity', 1299.99, 30),
                                                                                (2, 'T-shirt', 'Comfortable cotton T-shirt', 19.99, 100),
                                                                                (3, 'Coffee Maker', 'Automatic coffee maker for home', 79.99, 20);

INSERT INTO Transaction (product_id, quantity, transaction_type) VALUES
                                                                     (1, 10, 'IN'),
                                                                     (2, 5, 'IN'),
                                                                     (3, 15, 'OUT'),
                                                                     (4, 8, 'OUT');

