-- Creating database

CREATE DATABASE "Payments"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Greek_Greece.1253'
    LC_CTYPE = 'Greek_Greece.1253'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Create custom enum type for field "status"
CREATE TYPE status AS ENUM ('success','failure', 'pending');
-- Create Payment table
CREATE TABLE public."Payment"
(
    id integer NOT NULL,
    user_id integer NOT NULL,
    order_id integer NOT NULL,
    status status NOT NULL,
    CONSTRAINT "Payment_pkey" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Payment"
    OWNER to postgres;