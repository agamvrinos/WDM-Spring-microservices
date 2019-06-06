CREATE TABLE public.payment
(
    id        bigserial    NOT NULL PRIMARY KEY,
    user_id   bigint       NOT NULL,
    order_id  bigint       NOT NULL,
    status    varchar(100) NOT NULL
) TABLESPACE pg_default;

CREATE UNIQUE INDEX payment_user_id_uindex ON public.payment (order_id);

ALTER TABLE public.payment OWNER to wdm;