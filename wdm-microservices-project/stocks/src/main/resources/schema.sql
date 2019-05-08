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