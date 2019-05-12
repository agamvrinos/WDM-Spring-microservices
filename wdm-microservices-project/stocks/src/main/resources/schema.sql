-- Creating database
CREATE DATABASE "Stock"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE public.item
(
    id bigserial NOT NULL,
    title text NOT NULL,
    stock integer NOT NULL,
    price real NOT NULL,
    CONSTRAINT "Item_pkey" PRIMARY KEY (id)
)
TABLESPACE pg_default;

ALTER TABLE public.item
    OWNER to postgres;
