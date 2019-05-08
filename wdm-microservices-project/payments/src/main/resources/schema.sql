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