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
<<<<<<< HEAD
    order_id    integer NOT NULL,
    item_id     integer NOT NULL,
    "number"    integer NOT NULL,
=======
    order_id  bigint  NOT NULL,
    item_id   bigint  NOT NULL,
    "number"  integer NOT NULL,
>>>>>>> 32027187ed6a493822919642e92800a5ed6ce099
    CONSTRAINT "OrderItems_pkey" PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES public.order(id)
)
TABLESPACE pg_default;
<<<<<<< HEAD

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
=======
ALTER TABLE public.order_items OWNER to postgres;
TABLESPACE pg_default;
ALTER TABLE public.order OWNER to postgres;
>>>>>>> 32027187ed6a493822919642e92800a5ed6ce099
