-- Creating database
CREATE DATABASE "Stock"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Greek_Greece.1253'
    LC_CTYPE = 'Greek_Greece.1253'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE public."Item"
(
    id integer NOT NULL,
    title text COLLATE pg_catalog."default" NOT NULL,
    stock integer NOT NULL,
    price real NOT NULL,
    CONSTRAINT "Item_pkey" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Item"
    OWNER to postgres;