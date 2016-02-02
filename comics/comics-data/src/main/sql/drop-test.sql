drop table if exists "comics-test".comic_actor_relation cascade;
drop table if exists "comics-test".actor cascade;
drop table if exists "comics-test".comic_book cascade;
drop table if exists "comics-test".comic_book_series cascade;

drop sequence if exists "comics-test".comic_book_series_sequence cascade;
drop sequence if exists "comics-test".comic_book_sequence cascade;
drop sequence if exists "comics-test".actor_sequence cascade;
drop sequence if exists "comics-test".comic_actor_relation_sequence cascade;