create table if not exists authors (
    author_id   bigserial,
    full_name   varchar(255),
    primary key (author_id)
);

create table if not exists genres (
    genre_id    bigserial,
    name        varchar(255) not null,
    primary key (genre_id)
);

create table if not exists books (
    book_id     bigserial,
    title       varchar(255) not null,
    author_id   bigint,
    genre_id    bigint,
    primary key (book_id),
    foreign key (author_id) references authors (author_id) on delete cascade,
    foreign key (genre_id) references genres (genre_id) on delete cascade
);

create table if not exists comments (
    comment_id  bigserial,
    message     varchar(255) not null,
    book_id     bigint,
    primary key (comment_id),
    foreign key (book_id) references books(book_id) on delete set null
);
