CREATE USER expatrio WITH PASSWORD 'expatrio' CREATEDB;
CREATE DATABASE expatrio
    WITH
    OWNER = expatrio
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;