--liquibase formatted sql

--changeset example:1
ALTER TABLE users
    ADD COLUMN image VARCHAR(64);

--changeset example:2
ALTER TABLE users_aud
    ADD COLUMN image VARCHAR(64);
