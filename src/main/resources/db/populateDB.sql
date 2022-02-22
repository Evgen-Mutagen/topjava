DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

TRUNCATE TABLE users, user_roles, meals;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES  (100000, '2020-01-30 07:00', 'Завтрак', 800),
        (100000, '2020-01-30 12:00', 'Обед', 1000),
        (100000, '2020-01-30 19:00', 'Ужин', 300),
        (100001, '2020-01-30 07:00', 'Завтрак', 300),
        (100001, '2020-01-30 12:00', 'Обед', 500),
        (100001, '2020-01-30 19:00', 'Ужин', 300),
        (100001, '2020-01-31 07:00', 'Завтрак', 800),
        (100001, '2020-01-31 12:00', 'Обед', 500),
        (100001, '2020-01-31 19:00', 'Ужин', 800);