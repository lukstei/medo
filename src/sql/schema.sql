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