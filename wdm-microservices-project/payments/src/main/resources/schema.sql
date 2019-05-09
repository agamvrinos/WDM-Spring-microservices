-- Creating database

CREATE DATABASE "Payments"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

\c Payments

-- Create custom enum type for field "status"
CREATE TYPE status AS ENUM ('success', 'failure', 'pending');
-- Create Payment table
CREATE TABLE public.payment
(
    user_id integer NOT NULL,
    order_id integer NOT NULL,
    status status NOT NULL,
    CONSTRAINT "payment_pkey" PRIMARY KEY (order_id)
)
TABLESPACE pg_default;

ALTER TABLE public.payment
    OWNER to postgres;
