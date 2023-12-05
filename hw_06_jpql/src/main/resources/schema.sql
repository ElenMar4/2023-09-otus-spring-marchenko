create table authors (
    author_id   bigserial,
    full_name   varchar(255),
    primary key (author_id)
);

create table if not exists genres (
    genre_id    bigserial,
    name        varchar(255),
    primary key (genre_id)
);

create table if not exists books (
    book_id     bigserial,
    title       varchar(255),
    author_id   bigint,
    primary key (book_id),
    foreign key (author_id) references authors (author_id) on delete cascade
);

create table if not exists books_genres (
    book_id     bigint,
    genre_id    bigint,
    primary key (book_id, genre_id),
    foreign key (book_id) references books(book_id) on delete cascade,
    foreign key (genre_id) references genres(genre_id) on delete cascade
);

create table if not exists comments (
    comment_id  bigserial,
    message     varchar(255),
    book_id     bigint,
    primary key (comment_id),
    foreign key (book_id) references books(book_id)
);