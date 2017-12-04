DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2017-10-19 09:00', 'Завтрак', 200, 100000),
  ('2017-10-19 13:00', 'Обед', 500, 100000),
  ('2017-10-19 19:00', 'Ужин', 1000, 100000),
  ('2017-10-19 09:00', 'Завтрак', 1000, 100001),
  ('2017-10-19 13:00', 'Обед', 1000, 100001),
  ('2017-10-19 19:00', 'Ужин', 1000, 100001);
