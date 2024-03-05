insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(message, book_id)
values  ('comment_1', 1), ('comment_2', 1), ('comment_3', 1),
        ('comment_4', 2), ('comment_5', 2), ('comment_6', 2),
        ('comment_7', 3), ('comment_8', 3), ('comment_9', 3);

insert into users(username, password, role)
values ('user', '$2a$12$exrXjS/7ypSdG0FQxusI2ujclGX94usLJx62sgdSstQRCj7aTaDsu', 'ROLE_USER'),
        ('admin', '$2a$12$N6kdXFPLYanqwdcbzsR.nOsBkk4mYL53dUqcR/urFKNHVw9Y.hbKy', 'ROLE_ADMIN');