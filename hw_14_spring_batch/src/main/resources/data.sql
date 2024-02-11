merge into authors(full_name) key (full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

merge into genres(name) key (name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

merge into books(title, author_id, genre_id) key (title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

merge into comments(message, book_id) key(message, book_id)
values  ('comment_1_by_book_1', 1), ('comment_2_by_book_1', 1), ('comment_3_by_book_1', 1),
        ('comment_4_by_book_2', 2), ('comment_5_by_book_2', 2), ('comment_6_by_book_2', 2),
        ('comment_7_by_book_3', 3), ('comment_8_by_book_3', 3), ('comment_9_by_book_3', 3);
