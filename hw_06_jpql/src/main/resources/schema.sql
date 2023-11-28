create table authors (
    author_id bigserial,
    full_name varchar(255),
    primary key (author_id)
);

create table genres (
    genre_id bigserial,
    name varchar(255),
    primary key (genre_id)
);

create table books (
    book_id bigserial,
    title varchar(255),
    author_id bigint references authors (author_id) on delete cascade,
    primary key (book_id)
);

create table books_genres (
    book_id bigint references books(book_id) on delete cascade,
    genre_id bigint references genres(genre_id) on delete cascade,
    primary key (book_id, genre_id)
);

create table comments (
    comment_id bigserial,
    message varchar(255),
    book_id bigint references books(book_id),
    primary key (comment_id)
);