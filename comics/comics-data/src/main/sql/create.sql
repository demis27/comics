create sequence comics.comic_book_series_sequence;
create table comics.comic_book_series (
    comic_book_series_id        integer default nextval ('comics.comic_book_series_sequence') not null,
    creation_date               timestamp without time zone default current_date not null,
    modification_date           timestamp without time zone default current_date not null,
    title                       character varying(256) not null,
    constraint comic_book_series_pk primary key (comic_book_series_id)
);

create sequence comics.comic_book_sequence;
create table comics.comic_book (
    comic_book_id               integer default nextval ('comics.comic_book_sequence') not null,
    creation_date               timestamp without time zone default current_date not null,
    modification_date           timestamp without time zone default current_date not null,
    title                       character varying(256) not null,
    summary                     text,
    isbn                        character varying(17),
    comic_book_series_id        integer null,
    constraint comic_book_pk primary key (comic_book_id),
    constraint comic_book_series_fk foreign key (comic_book_series_id)
            references comics.comic_book_series (comic_book_series_id)
);



create sequence comics.actor_sequence;
create table comics.actor (
    actor_id                    integer default nextval ('comics.actor_sequence') not null,
    creation_date               timestamp without time zone default current_date not null,
    modification_date           timestamp without time zone default current_date not null,
    cover_name                  character varying(256) not null,
    constraint actor_pk primary key (actor_id)
);

create sequence comics.comic_actor_relation_sequence;
create table comics.comic_actor_relation (
    comic_actor_relation_id     integer default nextval ('comics.comic_actor_relation_sequence') not null,
    comic_book_id               integer not null,
    actor_id                    integer not null,
    role                        character varying(1),
    creation_date               timestamp without time zone default current_date not null,
    modification_date           timestamp without time zone default current_date not null,
    constraint actor_book_sequence_pk primary key (comic_actor_relation_id),
    constraint actor_book_role_book_fk foreign key (comic_book_id)
        references comics.comic_book (comic_book_id),
    constraint actor_book_role_actor_fk foreign key (actor_id)
        references comics.actor (actor_id)
);




