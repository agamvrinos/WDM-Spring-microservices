-- Creating database
CREATE DATABASE "Users"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE public.user
(
    id     bigserial  NOT NULL PRIMARY KEY,
    name   text       NOT NULL,
    credit integer    NOT NULL
) TABLESPACE pg_default;

CREATE TABLE public.journal
(
  transaction_id bigserial    NOT NULL,
  event          varchar(100) NOT NULL,
  status         varchar(100) NOT NULL,
  CONSTRAINT "Events_pkey" PRIMARY KEY (transaction_id, event)
) TABLESPACE pg_default;

ALTER TABLE public.user OWNER to postgres;
ALTER TABLE public.journal OWNER to postgres;
