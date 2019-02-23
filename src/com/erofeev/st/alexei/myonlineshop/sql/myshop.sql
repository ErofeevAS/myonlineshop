CREATE  database IF NOT EXISTS  myshop;

USE myshop;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS permissions;


CREATE TABLE IF NOT EXISTS items(
id BIGINT  auto_increment PRIMARY KEY,
name VARCHAR(255),
description text,
UNIQUE_number VARCHAR(255) UNIQUE,
price DECIMAL(10,4)
);

CREATE TABLE IF NOT EXISTS permissions(
id BIGINT auto_increment PRIMARY KEY,
name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS  roles (
id BIGINT auto_increment PRIMARY KEY,
name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS role_permission(
role_id BIGINT,
permission_id BIGINT,
PRIMARY KEY(role_id,permission_id)
);

CREATE TABLE IF NOT EXISTS users(
id BIGINT auto_increment PRIMARY KEY,
email VARCHAR(255),
name VARCHAR(30),
surname VARCHAR(30),
password VARCHAR(255),
role_id BIGINT
);

CREATE TABLE IF NOT EXISTS profiles(
id BIGINT  auto_increment PRIMARY KEY,
address VARCHAR(255),
telephone VARCHAR(255),
user_id BIGINT
);

CREATE TABLE IF NOT EXISTS orders(
id BIGINT auto_increment PRIMARY KEY,
created date,
quantity int,
status ENUM('NEW', 'REVIEWING', 'IN_PROCESS', 'DELIVERED') NOT NULL,
user_id BIGINT,
item_id BIGINT
);

CREATE TABLE IF NOT EXISTS stock(
item_id BIGINT,
quantity int
);



ALTER TABLE role_permission ADD FOREIGN KEY(role_id) REFERENCES roles(id);
ALTER TABLE role_permission ADD FOREIGN KEY(permission_id) REFERENCES permissions(id);

ALTER TABLE orders ADD FOREIGN KEY(user_id) REFERENCES users(id);
ALTER TABLE orders ADD FOREIGN KEY(item_id) REFERENCES items(id);

ALTER TABLE profiles ADD FOREIGN KEY(user_id) REFERENCES users(id);

ALTER TABLE users ADD FOREIGN KEY(role_id) REFERENCES roles(id);

ALTER TABLE stock ADD FOREIGN KEY(item_id) REFERENCES items(id);






