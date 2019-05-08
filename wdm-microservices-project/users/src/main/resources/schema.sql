CREATE TABLE public."User"
(
    id integer NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    credit real NOT NULL,
    CONSTRAINT "User_pkey" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."User"
    OWNER to postgres;