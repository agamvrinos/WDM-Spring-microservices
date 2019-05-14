-- Creating database
CREATE DATABASE "Stock"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE public.item
(
    id    bigserial    NOT NULL PRIMARY KEY,
    title varchar(100) NOT NULL,
    stock integer      NOT NULL,
    price integer      NOT NULL
)

TABLESPACE pg_default;
ALTER TABLE public.item OWNER to postgres;
