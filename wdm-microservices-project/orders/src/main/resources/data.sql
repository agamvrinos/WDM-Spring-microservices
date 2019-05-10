--
-- Data for Name: Order; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."order" (id, user_id, total) VALUES (1, 4, 1015.57001);
INSERT INTO public."order" (id, user_id, total) VALUES (2, 3, 391.5);
INSERT INTO public."order" (id, user_id, total) VALUES (3, 1, 265.959991);
INSERT INTO public."order" (id, user_id, total) VALUES (4, 2, 8146.97998);
INSERT INTO public."order" (id, user_id, total) VALUES (5, 5, 10691.46);
INSERT INTO public."order" (id, user_id, total) VALUES (6, 4, 255);
INSERT INTO public."order" (id, user_id, total) VALUES (7, 1, 3398);


--
-- Data for Name: OrderItems; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (1, 13, 1);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (1, 14, 1);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (1, 7, 1);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (1, 8, 1);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (1, 9, 1);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (1, 10, 1);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (1, 11, 2);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (2, 5, 9);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (3, 6, 4);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (4, 1, 2);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (4, 4, 3);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (5, 2, 18);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (6, 12, 1);
INSERT INTO public."orderItems" (order_id, item_id, number) VALUES (7, 3, 2);


--
-- PostgreSQL database dump complete
--

