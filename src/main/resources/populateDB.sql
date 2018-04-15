DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO user_roles (role_name) VALUES
  ('USER'),
  ('ADMIN');

INSERT INTO users (name, password, role_id, enabled) VALUES
('user', 'user', '100000', 'true'),
('root', 'root', '100001', 'true');