/* public.order table*/
CREATE TABLE public.order
(
    id      bigserial NOT NULL PRIMARY KEY,
    user_id bigint    NOT NULL,
    total   real      NOT NULL
) TABLESPACE pg_default;

/* public.order_items table*/
CREATE TABLE public.order_items
(
    order_id  bigint  NOT NULL,
    item_id   bigint  NOT NULL,
    "number"  integer NOT NULL,
    CONSTRAINT "OrderItems_pkey" PRIMARY KEY (order_id, item_id),
    FOREIGN KEY (order_id) REFERENCES public.order(id)
) TABLESPACE pg_default;

CREATE TABLE public.journal
(
transaction_id  bigserial    NOT NULL,
event           varchar(100) NOT NULL,
status          varchar(100) NOT NULL,
price           varchar(100) NULL,
CONSTRAINT "Events_pkey" PRIMARY KEY (transaction_id, event),
FOREIGN KEY (transaction_id) REFERENCES public.order(id)
) TABLESPACE pg_default;

ALTER TABLE public.order_items OWNER to wdm;
ALTER TABLE public.order OWNER to wdm;
ALTER TABLE public.journal OWNER to wdm;