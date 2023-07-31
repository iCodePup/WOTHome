-- Cleanup (not anymore)
-- DROP DATABASE IF EXISTS postgres;
-- CREATE DATABASE postgres;
-- USE postgres;

-- Create
CREATE TABLE enduser
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
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100) NOT NULL unique,
    url   VARCHAR(100) NOT NULL unique,
    alive BOOLEAN DEFAULT FALSE
);

create table houseplan
(
    id SERIAL PRIMARY KEY
);

create table room
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL unique,
    surface DECIMAL      NOT NULL
);

alter table enduser
    add column email varchar(20) unique,
    add foreign key (email) references wot_user (email);

alter table thing
    add column enduserid INT NULL DEFAULT NULL,
    add column roomid    INT NULL DEFAULT NULL,
    add foreign key (enduserid) references enduser (id),
    add foreign key (roomid) references room (id) on delete set null;

alter table houseplan
    add column enduserid INT NULL DEFAULT NULL,
    add foreign key (enduserid) references enduser (id);

alter table room
    add column houseplanid INT NULL DEFAULT NULL,
    add foreign key (houseplanid) references houseplan (id);