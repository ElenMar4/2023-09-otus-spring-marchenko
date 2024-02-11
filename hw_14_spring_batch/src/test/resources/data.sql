merge into authors(full_name) key (full_name)
values ('Author_test');

merge into genres(name) key (name)
values ('Genre_test');

merge into books(title, author_id, genre_id) key (title, author_id, genre_id)
values ('BookTest_title', 1, 1);

merge into comments(message, book_id) key(message, book_id)
values  ('comment_test', 1);
