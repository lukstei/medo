create table users(
  username character varying not null primary key,
  password character varying not null,
  enabled boolean not null
);

create table authorities (
  username character varying not null,
  authority character varying not null,
  constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

insert into users (username, password, enabled) values ('admin', '$2a$10$H2cY7hgjmi2g94KcvD0xFO88gnLuZKBWzdAXGa8f4x841ghSJgbk.', true);

insert into authorities (username, authority) values ('admin', 'article.create');
insert into authorities (username, authority) values ('admin', 'article.edit');
insert into authorities (username, authority) values ('admin', 'admin');

create table article_type
(
  id int primary key not null,
  name character varying not null unique
);

insert into article_type(id, name) values (0, 'Interview');
insert into article_type(id, name) values (1, 'Kommentar');

create sequence author_seq;
create table author
(
  id int primary key not null default nextval('author_seq'),
  name character varying not null unique
);

create sequence media_seq;
create table media
(
  id int primary key not null default nextval('media_seq'),
  name character varying not null unique
);

create sequence article_seq;
create table article
(
  id int primary key not null default nextval('article_seq'),
  article_type int not null references article_type(id),
  txt text not null,
  author int references author(id),
  article_date date  not null,
  media int not null references media(id),

  fulltext_index tsvector not null
);
create index article_fulltext_idx on article using gin(fulltext_index);
create index article_date_idx on article(article_date);

create trigger article_index_trigger
before insert or update on article for each row
execute procedure tsvector_update_trigger(fulltext_index, 'pg_catalog.german', txt);