-- Creating database

CREATE DATABASE "Orders"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Creating database tables
CREATE TABLE public."Order"
(
    id integer NOT NULL,
    user_id integer NOT NULL,
    total real NOT NULL,
    CONSTRAINT "Order_pkey" PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."Order"
    OWNER to postgres;


CREATE TABLE public."OrderItems"
(
    order_id integer NOT NULL,
    item_id integer NOT NULL,
    "number" integer NOT NULL,
    CONSTRAINT "OrderItems_pkey" PRIMARY KEY (order_id, item_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."OrderItems"
    OWNER to postgres;