merge into authors(full_name) key (full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

merge into genres(name) key (name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

merge into books(title, author_id, genre_id) key (title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

merge into comments(message, book_id) key(message, book_id)
values  ('comment_1', 1), ('comment_2', 1), ('comment_3', 1),
        ('comment_4', 2), ('comment_5', 2), ('comment_6', 2),
        ('comment_7', 3), ('comment_8', 3), ('comment_9', 3);

merge into users(username, password) key (username, password)
values ('User_1', '$2a$12$7BxiDXeoL7ATd54FhW.8zu5DZMDEeGKxlBMqd4qTtsetmfA19qQXS');