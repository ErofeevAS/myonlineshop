INSERT INTO permissions (name) VALUES('create_item');
INSERT INTO permissions (name) VALUES('delete_item');
INSERT INTO permissions (name) VALUES('upload_with_xml_item');
INSERT INTO permissions (name) VALUES('show_order');
INSERT INTO permissions (name) VALUES('change_order_status');
INSERT INTO permissions (name) VALUES('create_order');
INSERT INTO permissions (name) VALUES('change_profile');
INSERT INTO permissions (name) VALUES('delete_user');
INSERT INTO permissions (name) VALUES('block_user');
INSERT INTO permissions (name) VALUES('unblock_user');




INSERT INTO roles (name) VALUES('admin');
INSERT INTO roles (name) VALUES('user');
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

INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='user'),(SELECT id FROM permissions WHERE permissions.name ='create_item'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='user'),(SELECT id FROM permissions WHERE permissions.name ='delete_item'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='user'),(SELECT id FROM permissions WHERE permissions.name ='upload_with_xml_item'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='user'),(SELECT id FROM permissions WHERE permissions.name ='show_order'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='user'),(SELECT id FROM permissions WHERE permissions.name ='change_order_status'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='user'),(SELECT id FROM permissions WHERE permissions.name ='change_profile'));

INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='customer'),(SELECT id FROM permissions WHERE permissions.name ='create_order'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='customer'),(SELECT id FROM permissions WHERE permissions.name ='show_order'));
INSERT INTO role_permission (role_id,permission_id) VALUES((SELECT id FROM roles WHERE name='customer'),(SELECT id FROM permissions WHERE permissions.name ='change_profile'));




INSERT INTO users (email, name, surname, password, role_id) values ('spokeman152@gmail.com','Alexei','Erofeev','1234',1);