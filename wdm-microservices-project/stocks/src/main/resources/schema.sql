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
) TABLESPACE pg_default;

CREATE TABLE public.journal
(
transaction_id bigserial    NOT NULL,
event          varchar(100) NOT NULL,
status         varchar(100) NOT NULL,
price          integer      NULL,
CONSTRAINT "Journal_pkey" PRIMARY KEY (transaction_id)
) TABLESPACE pg_default;

ALTER TABLE public.item OWNER to postgres;
ALTER TABLE public.journal OWNER to postgres;
CREATE INDEX item_index ON public.item(id);
