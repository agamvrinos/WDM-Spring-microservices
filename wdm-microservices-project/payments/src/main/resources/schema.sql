-- Creating database

CREATE DATABASE "Payments"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Create Payment table
CREATE TABLE public.payment
(
    id        bigserial  NOT NULL PRIMARY KEY,
    user_id   bigint       NOT NULL,
    order_id  bigint       NOT NULL,
    status    varchar(100) NOT NULL
)
CREATE UNIQUE INDEX payment_user_id_uindex ON public.user_id (FSFS);

TABLESPACE pg_default;

ALTER TABLE public.payment OWNER to postgres;
