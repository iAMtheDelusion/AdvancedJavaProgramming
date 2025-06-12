--------------------------------------------------------------
--Module 01 Course Project - Development Phase: Testing     --
--                                                          --
--Devin Gast                                                --
--Rasmussen University                                      --
--Advanced Java Programming                                 --
--Professor Kumar                                           --
--May 25th, 2025                                            --
--------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS order_system; -- this is to create the database
USE order_system; -- use the dateabase

CREATE TABLE users ( -- creating the user table(regular users or admins)
    user_id INT AUTO_INCREMENT PRIMARY KEY, -- User ID for each user
    username VARCHAR(50) NOT NULL, -- login name
    password VARCHAR(100) NOT NULL, -- password
    role VARCHAR(20) DEFAULT 'customer' -- custoemr or admin
);

CREATE TABLE products ( -- creates the products table
    product_id INT AUTO_INCREMENT PRIMARY KEY, -- unique is for EACH product.
    name VARCHAR(100) NOT NULL, -- product name
    description TEXT, -- details about each product
    quantity INT NOT NULL, -- how many
    price DECIMAL(10,2) NOT NULL -- money limited to two decimal places.
);

CREATE TABLE orders ( -- order table is created
    order_id INT AUTO_INCREMENT PRIMARY KEY, -- unique id for EACH order
    user_id INT, -- which user placed the order, tied to USER ID
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- time order was made
    total_price DECIMAL(10,2), -- total cost of the order
    FOREIGN KEY (user_id) REFERENCES users(user_id) -- link to the user
);

CREATE TABLE order_items ( -- creates the order_items table to order za items :)
    order_item_id INT AUTO_INCREMENT PRIMARY KEY, -- unique id for each row
    order_id INT, -- which order this belongs to
    product_id INT, -- which products is being ordered
    quantity INT NOT NULL, -- how many of that item
    FOREIGN KEY (order_id) REFERENCES orders(order_id), -- link to orders
    FOREIGN KEY (product_id) REFERENCES products(product_id) -- link to za products :)
); --end scene.
