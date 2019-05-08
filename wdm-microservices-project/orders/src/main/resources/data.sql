--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2
-- Dumped by pg_dump version 11.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: Order; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."Order" (id, user_id, total) VALUES (1, 4, 1015.57001);
INSERT INTO public."Order" (id, user_id, total) VALUES (2, 3, 391.5);
INSERT INTO public."Order" (id, user_id, total) VALUES (3, 1, 265.959991);
INSERT INTO public."Order" (id, user_id, total) VALUES (4, 2, 8146.97998);
INSERT INTO public."Order" (id, user_id, total) VALUES (5, 5, 10691.46);
INSERT INTO public."Order" (id, user_id, total) VALUES (6, 4, 255);
INSERT INTO public."Order" (id, user_id, total) VALUES (7, 1, 3398);


--
-- Data for Name: OrderItems; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (1, 13, 1);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (1, 14, 1);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (1, 7, 1);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (1, 8, 1);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (1, 9, 1);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (1, 10, 1);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (1, 11, 2);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (2, 5, 9);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (3, 6, 4);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (4, 1, 2);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (4, 4, 3);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (5, 2, 18);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (6, 12, 1);
INSERT INTO public."OrderItems" (order_id, item_id, number) VALUES (7, 3, 2);


--
-- PostgreSQL database dump complete
--

