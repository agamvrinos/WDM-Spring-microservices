-- Creating database
CREATE DATABASE "Users"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
\c "Users"
CREATE TABLE public.user
(
    id serial NOT NULL,
    name text NOT NULL,
    credit real NOT NULL,
    CONSTRAINT "User_pkey" PRIMARY KEY (id)
)
TABLESPACE pg_default;

ALTER TABLE public.user
    OWNER to postgres;
