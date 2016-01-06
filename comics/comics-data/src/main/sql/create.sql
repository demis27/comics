create sequence "comics".comic_book_sequence;
create table "comics".comic_book (
    comic_book_id       integer default nextval ('"comics".comic_book_sequence') not null,
    creation_date       timestamp without time zone default current_date not null,
    modification_date   timestamp without time zone default current_date not null,
    "title"             character varying(256),
    constraint comic_book_pk primary key (comic_book_id)
);
