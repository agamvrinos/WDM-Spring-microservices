-- Creating database

CREATE DATABASE "Orders"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

/* public.order table*/
CREATE TABLE public.order
(
    id      bigserial NOT NULL PRIMARY KEY,
    user_id bigint    NOT NULL,
    total   real      NOT NULL
)
/* public.order_items table*/
CREATE TABLE public.order_items
(
    order_id  bigint  NOT NULL,
    item_id   bigint  NOT NULL,
    "number"  integer NOT NULL,
    CONSTRAINT "OrderItems_pkey" PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES public.order(id)
)
TABLESPACE pg_default;
ALTER TABLE public.order_items OWNER to postgres;
TABLESPACE pg_default;
ALTER TABLE public.order OWNER to postgres;
