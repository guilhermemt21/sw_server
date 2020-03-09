--liquibase formatted sql

--changeset guilhermemt21:1
create table planets (
  id int(11) UNSIGNED not null primary key auto_increment,
  name VARCHAR(255),
  terrain VARCHAR(255),
  climate VARCHAR(255),
  apparitions INTEGER,
  UNIQUE KEY name_unique (name)
);