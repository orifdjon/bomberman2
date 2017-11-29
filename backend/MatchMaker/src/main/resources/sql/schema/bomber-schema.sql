begin;

drop schema if exists bomber cascade;
create schema bomber;

drop table if exists bomber.gs;
create table bomber.gs (
  gameId integer  UNIQUE not null,
  user1 VARCHAR(30),
  user2 VARCHAR(30),
  user3 VARCHAR(30),
  user4 VARCHAR(30),
  time   timestamp    not null,


  primary key (gameId)
);

commit;
