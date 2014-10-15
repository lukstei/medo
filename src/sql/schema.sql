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
  media int not null
);