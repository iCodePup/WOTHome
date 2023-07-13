-- Cleanup (not anymore)
-- DROP DATABASE IF EXISTS postgres;
-- CREATE DATABASE postgres;
-- USE postgres;

-- Create
CREATE TABLE user
(
    id        SERIAL PRIMARY KEY,
    telephone VARCHAR(10),
    address   VARCHAR(50)
);

create table wot_user
(
    email     varchar(20),
    firstname VARCHAR(50)  NOT NULL,
    lastname  VARCHAR(50)  NOT NULL,
    primary key (email),
    role      varchar(10), -- "ADMIN" ou "CLIENT"
    password  varchar(500) not null
);

create table thing
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    typeAsJson  TEXT
);

alter table user
    add column email varchar(20) unique,
    add foreign key (email) references wot_user (email);

