INSERT INTO permissions (name) VALUES('CREATE_ITEM');
INSERT INTO permissions (name) VALUES('DELETE_ITEM');
INSERT INTO permissions (name) VALUES('UPLOAD_WITH_XML_ITEM');
INSERT INTO permissions (name) VALUES('SHOW_ORDER');
INSERT INTO permissions (name) VALUES('CHANGE_ORDER_STATUS');
INSERT INTO permissions (name) VALUES('CREATE_ORDER');
INSERT INTO permissions (name) VALUES('CHANGE_PROFILE');
INSERT INTO permissions (name) VALUES('DELETE_USER');
INSERT INTO permissions (name) VALUES('BLOCK_USER');
INSERT INTO permissions (name) VALUES('UNBLOCK_USER');

INSERT INTO roles (name) VALUES('admin');
INSERT INTO roles (name) VALUES('seller');
INSERT INTO roles (name) VALUES('customer');

INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='create_item' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='delete_item' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='upload_with_xml_item' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='show_order' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='change_order_status' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='create_order' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='change_profile' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='delete_user' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='block_user' ));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='admin'),(SELECT id FROM permissions WHERE permissions.name='unblock_user' ));

INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='seller'),(SELECT id FROM permissions WHERE permissions.name ='create_item'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='seller'),(SELECT id FROM permissions WHERE permissions.name ='delete_item'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='seller'),(SELECT id FROM permissions WHERE permissions.name ='upload_with_xml_item'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='seller'),(SELECT id FROM permissions WHERE permissions.name ='show_order'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='seller'),(SELECT id FROM permissions WHERE permissions.name ='change_order_status'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='seller'),(SELECT id FROM permissions WHERE permissions.name ='change_profile'));

INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='customer'),(SELECT id FROM permissions WHERE permissions.name ='create_order'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='customer'),(SELECT id FROM permissions WHERE permissions.name ='show_order'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='customer'),(SELECT id FROM permissions WHERE permissions.name ='change_profile'));


INSERT INTO users (email, name, surname, password, role_id) values ('sp@gmail.com','Alexei','Erofeev','81dc9bdb52d04dc20036dbd8313ed055',(SELECT id FROM roles WHERE name='admin'));
INSERT INTO users (email, name, surname, password, role_id) values ('seller666@gmail.com','Bob','Black','81dc9bdb52d04dc20036dbd8313ed055',(SELECT id FROM roles WHERE name='seller'));
INSERT INTO users (email, name, surname, password, role_id) values ('customer13@gmail.com','Ivan','Fool','81dc9bdb52d04dc20036dbd8313ed055',(SELECT id FROM roles WHERE name='customer'));