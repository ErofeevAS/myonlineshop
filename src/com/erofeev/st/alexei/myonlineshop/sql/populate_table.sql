INSERT INTO permissions (name) VALUES('SELLER');
INSERT INTO permissions (name) VALUES('CUSTOMER');

INSERT INTO roles (name) VALUES('seller');
INSERT INTO roles (name) VALUES('customer');

INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='seller'),(SELECT id FROM permissions WHERE permissions.name='SELLER' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='customer'),(SELECT id FROM permissions WHERE permissions.name='CUSTOMER' ));

INSERT INTO users (email, name, surname, password, role_id) values ('user1@gmail.com','Alexei','Brick','81dc9bdb52d04dc20036dbd8313ed055',(SELECT id FROM roles WHERE name='customer'));
INSERT INTO users (email, name, surname, password, role_id) values ('user2@gmail.com','Bob','Black','81dc9bdb52d04dc20036dbd8313ed055',(SELECT id FROM roles WHERE name='seller'));
INSERT INTO users (email, name, surname, password, role_id) values ('user3@gmail.com','Ivan','Fool','81dc9bdb52d04dc20036dbd8313ed055',(SELECT id FROM roles WHERE name='customer'));