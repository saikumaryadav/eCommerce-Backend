CREATE DATABASE IF NOT EXISTS ecommerce;
USE ecommerce;

-- Create MySQL user and grant permissions
CREATE USER IF NOT EXISTS 'appuser'@'%' IDENTIFIED BY 'apppassword';
GRANT ALL PRIVILEGES ON ecommerce.* TO 'appuser'@'%';
FLUSH PRIVILEGES;

-- Create user table
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create category table
CREATE TABLE IF NOT EXISTS category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Create product table
CREATE TABLE IF NOT EXISTS product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    stock INT NOT NULL,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Create cart table
CREATE TABLE IF NOT EXISTS cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    category_id INT,
    product_id INT,
    user_id INT,
    quantity INT,
    address VARCHAR(255),  -- Changed back to 'address'
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    address VARCHAR(255) NOT NULL,  -- Changed from 'delivery_address' to 'address'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Insert sample data into user
INSERT INTO user (name, email, password) VALUES
('Dharma', 'dharma@gmail.com', '12345'),
('Alice', 'alice@gmail.com', '12345'),
('Bob', 'bob@gmail.com', '12345');

-- Insert sample data into category
INSERT INTO category (name) VALUES 
('electronic'),
('clothes'),
('food');

-- Insert sample data into product (10 items per category)
INSERT INTO product (amount, description, name, stock, category_id) VALUES 
-- Electronics (category_id: 1)
(999.99, 'High-performance laptop with 16GB RAM', 'Laptop', 10, 1),
(599.99, 'Smartphone with 128GB storage', 'Smartphone', 20, 1),
(299.99, 'Noise-canceling wireless headphones', 'Headphones', 30, 1),
(149.99, '10-inch tablet with stylus', 'Tablet', 15, 1),
(89.99, 'Smartwatch with heart rate monitor', 'Smartwatch', 25, 1),
(199.99, '4K action camera with waterproof case', 'Action Camera', 12, 1),
(49.99, 'Wireless mouse with ergonomic design', 'Mouse', 50, 1),
(79.99, 'Mechanical keyboard with RGB lighting', 'Keyboard', 40, 1),
(399.99, '32-inch LED monitor', 'Monitor', 8, 1),
(129.99, 'Portable Bluetooth speaker', 'Speaker', 35, 1),

-- Clothes (category_id: 2)
(49.99, 'Cotton T-shirt in black', 'T-shirt', 50, 2),
(29.99, 'Denim jeans in blue', 'Jeans', 40, 2),
(79.99, 'Leather jacket in brown', 'Jacket', 20, 2),
(19.99, 'Cotton socks (pack of 5)', 'Socks', 100, 2),
(39.99, 'Hooded sweatshirt in gray', 'Sweatshirt', 30, 2),
(59.99, 'Formal shirt in white', 'Shirt', 25, 2),
(24.99, 'Summer dress in floral print', 'Dress', 35, 2),
(14.99, 'Baseball cap in black', 'Cap', 60, 2),
(69.99, 'Woolen scarf in red', 'Scarf', 15, 2),
(99.99, 'Sneakers in white', 'Sneakers', 20, 2),

-- Food (category_id: 3)
(5.99, 'Organic apples (1kg)', 'Apples', 100, 3),
(3.99, 'Fresh bananas (1kg)', 'Bananas', 120, 3),
(6.99, 'Whole grain bread (500g)', 'Bread', 80, 3),
(2.99, 'Organic milk (1L)', 'Milk', 150, 3),
(4.99, 'Free-range eggs (dozen)', 'Eggs', 90, 3),
(7.99, 'Dark chocolate bar (100g)', 'Chocolate', 60, 3),
(3.49, 'Orange juice (1L)', 'Orange Juice', 70, 3),
(8.99, 'Almonds (500g)', 'Almonds', 40, 3),
(1.99, 'Instant coffee (200g)', 'Coffee', 200, 3),
(4.49, 'Pasta (500g)', 'Pasta', 110, 3),
(4.49, 'Pasta (500g)', 'SKyyy', 110, 3);

-- Insert sample data into cart
INSERT INTO cart (amount, category_id, product_id, user_id, quantity, address) VALUES 
(999.99, 1, 1, 1, 1, '123 Main St, City'),
(49.99, 2, 11, 2, 2, '456 Oak Ave, Town'),
(5.99, 3, 21, 1, 5, '123 Main St, City');

-- Insert sample data into orders
INSERT INTO orders (user_id, product_id, address) VALUES 
(1, 1, '123 Main St, City'),
(2, 11, '456 Oak Ave, Town'),
(1, 21, '123 Main St, City');