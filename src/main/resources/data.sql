insert into roles(name) values ('ROLE_STUDENT');
insert into roles(name) values ('ROLE_ADMIN');

insert into users (password, username) values ('$2a$10$BUSGMkoS9gk0aYESejK/uOPOlXNrnpMHndHGq3NNdaRjPe8YuPi6S', 'admin');
insert into users (password, username) values ('$2a$10$aqo1VUbR8uNEC.xvxKg3oedyzjDBAEN.iaTcq3OanWOs/DaCOfKGy', 'student');

insert into courses (author, title) values ('Учебный центр Трайтек', '1 модуль (Java)');
insert into courses (author, title) values ('Учебный центр Трайтек', '2 модуль (Java)');

insert into lessons (text, title, course_id) values ('<b>1 урок <span style="background-color: rgb(255, 255, 0);">:)</span></b>', 'Знакомство с языком программирования Java.', 1);
insert into lessons (text, title, course_id) values ('2 урок', 'Работа с переменными.', 1);
insert into lessons (text, title, course_id) values ('3 урок', 'Основные алгоритмические конструкции.', 1);
insert into lessons (text, title, course_id) values ('4 урок', 'Основы объектно-ориентированного программирования.', 1);
insert into lessons (text, title, course_id) values ('5 урок', 'Основы компьютерной графики языка Java.', 1);

insert into courses_users (courses_id, users_id) values (1, 2);

insert into users_roles (users_id, roles_id) values (1, 2);
insert into users_roles (users_id, roles_id) values (2, 1);

insert into course_images (content_type, filename, course_id) values ('image/jpeg', '1', 1);
insert into course_images (content_type, filename, course_id) values ('image/jpeg', '2', 2);

insert into avatar_images (content_type, filename, user_id) values ('image/jpeg', '1', 1);
insert into avatar_images (content_type, filename, user_id) values ('image/jpeg', '2', 2);
