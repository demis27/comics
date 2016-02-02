drop table if exists comics.comic_actor_relation cascade;
drop table if exists comics.actor cascade;
drop table if exists comics.comic_book cascade;
drop table if exists comics.comic_book_series cascade;

drop sequence if exists comics.comic_book_series_sequence cascade;
drop sequence if exists comics.comic_book_sequence cascade;
drop sequence if exists comics.actor_sequence cascade;
drop sequence if exists comics.comic_actor_relation_sequence cascade;