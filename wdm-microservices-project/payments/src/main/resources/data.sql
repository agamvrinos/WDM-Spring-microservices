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
-- Data for Name: Payment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."Payment" (id, user_id, order_id, status) VALUES (1, 2, 4, 'success');
INSERT INTO public."Payment" (id, user_id, order_id, status) VALUES (2, 1, 7, 'failure');
INSERT INTO public."Payment" (id, user_id, order_id, status) VALUES (3, 3, 2, 'success');
INSERT INTO public."Payment" (id, user_id, order_id, status) VALUES (4, 4, 1, 'pending');


--
-- PostgreSQL database dump complete
--

