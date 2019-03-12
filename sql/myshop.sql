CREATE database IF NOT EXISTS myshop;

USE myshop;

CREATE TABLE IF NOT EXISTS items
(
  id            BIGINT auto_increment PRIMARY KEY,
  name          VARCHAR(255) NOT NULL,
  description   TEXT NOT NULL,
  unique_number VARCHAR(255) UNIQUE NOT NULL,
  price         DECIMAL(10, 4) NOT NULL,
  deleted       BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS permissions
(
  id   BIGINT auto_increment PRIMARY KEY,
  name ENUM ('SELLER', 'CUSTOMER') NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
  id   BIGINT auto_increment PRIMARY KEY,
  name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS role_permission
(
  role_id       BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE IF NOT EXISTS users
(
  id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  email    VARCHAR(255) NOT NULL,
  name     VARCHAR(30) NOT NULL,
  surname  VARCHAR(30) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_id  BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS profiles
(
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  address   VARCHAR(255) NOT NULL ,
  telephone VARCHAR(255) NOT NULL ,
  user_id   BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
  id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  created  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  quantity INT NOT NULL,
  status   ENUM ('NEW', 'REVIEWING', 'IN_PROCESS', 'DELIVERED') NOT NULL,
  user_id  BIGINT NOT NULL,
  item_id  BIGINT NOT NULL
);


ALTER TABLE role_permission
  ADD FOREIGN KEY (role_id) REFERENCES roles (id);
ALTER TABLE role_permission
  ADD FOREIGN KEY (permission_id) REFERENCES permissions (id);

ALTER TABLE orders
  ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE orders
  ADD FOREIGN KEY (item_id) REFERENCES items (id);

ALTER TABLE profiles
  ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
  ADD FOREIGN KEY (role_id) REFERENCES roles (id);






