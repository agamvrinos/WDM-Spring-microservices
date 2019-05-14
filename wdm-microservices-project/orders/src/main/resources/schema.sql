-- Creating database

CREATE DATABASE "Orders"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Creating database tables
CREATE TABLE public.order
(
    id serial NOT NULL ,
    user_id integer NOT NULL,
    total real NOT NULL,
    CONSTRAINT "Order_pkey" PRIMARY KEY (id)
)
TABLESPACE pg_default;

ALTER TABLE public.order
    OWNER to postgres;


CREATE TABLE public.orderitems
(
    order_id    integer NOT NULL,
    item_id     integer NOT NULL,
    "number"    integer NOT NULL,
    CONSTRAINT "OrderItems_pkey" PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES public.order(id)
)
TABLESPACE pg_default;

ALTER TABLE public.orderitems
    OWNER to postgres;


ALTER TABLE public.order
    ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN user_id TYPE BIGINT;

ALTER TABLE public.order_items
    ALTER COLUMN order_id TYPE BIGINT,
    ALTER COLUMN item_id TYPE BIGINT;

ALTER TABLE public.order
    ALTER COLUMN total TYPE INT;