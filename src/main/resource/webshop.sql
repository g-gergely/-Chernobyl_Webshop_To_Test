
--Customers-------------------------------------------------
ALTER TABLE IF EXISTS ONLY public.regdCustomer
DROP CONSTRAINT IF EXISTS pk_regdCustomer_id CASCADE;
DROP TABLE IF EXISTS public.regdCustomer cascade;
DROP SEQUENCE IF EXISTS public.regdCustomer cascade;

CREATE TABLE regdCustomer
(
    id  SERIAL NOT NULL,
    hashed_password varchar(255),
    user_name varchar(255),
    e_mail text unique,
    submission_time timestamp without time zone

);

ALTER TABLE ONLY regdCustomer
    ADD CONSTRAINT pk_regCustomer_id PRIMARY KEY (id);
-----------------------------------------------------------------



--Orders-------------------------------------------------------------------------------------------
alter table if exists only public.orders drop constraint if exists pk_order_id cascade;
drop table if exists public.orders cascade;
drop sequence if exists public.orders cascade;

CREATE TABLE orders
(
    id SERIAL NOT NULL,
    submission_time timestamp without time zone,
    orderCustomer int
);

ALTER TABLE ONLY orders
    ADD CONSTRAINT pk_order_id PRIMARY KEY (id);
ALTER TABLE ONLY orders
    ADD CONSTRAINT fk_regdCustomer_ foreign key (orderCustomer) references regdCustomer(id);
-------------------------------------------------------------------------------------------------------



--All orders----------------------------------------------------------------------------------------------
alter table if exists only public.all_orders drop constraint if exists pk_all_order_id cascade;
drop table if exists public.all_orders cascade;
drop sequence if exists public.all_orders cascade;

CREATE TABLE all_orders
(
    id serial not null,
    product_name varchar(255),
    product_price float,
    real_product_id int,          ----- when the product table is ready
    order_batch int
);

ALTER TABLE ONLY all_orders
    ADD CONSTRAINT pk_all_order_id PRIMARY KEY (id);
ALTER TABLE ONLY all_orders
    ADD CONSTRAINT fk_order_batch foreign key (order_batch) references orders(id);
ALTER TABLE ONLY all_orders
    ADD CONSTRAINT fk_real_product_id foreign key (real_product_id) references product(id);

--------------------------------------------------------------------------------------------------



--Product-------------------------------------------------------------------------
alter table if exists only public.product drop constraint if exists pk_product_id cascade;
drop table if exists public.product cascade ;
drop sequence if exists public.product cascade ;

CREATE TABLE product
(
    id int unique,
    name varchar(255),
    submission_time timestamp without time zone,
    supplier int,
    price float,
    category int
);

ALTER TABLE ONLY product
    ADD CONSTRAINT pk_product_id PRIMARY KEY (id);
ALTER TABLE ONLY product
    ADD CONSTRAINT fk_supplier_id foreign key (supplier) references supplier(id);
ALTER TABLE ONLY product
    ADD CONSTRAINT fk_category_id foreign key (category) references category(id);
------------------------------------------------------------------------------------------


--Product Category ------------------------------------------------------------------------

alter table if exists only public.category drop constraint if exists pk_category_id cascade;
drop table if exists public.category cascade ;
drop sequence if exists public.category cascade ;

CREATE TABLE category
(
    id int unique,
    name varchar(255),
    department text
);

ALTER TABLE ONLY category
    ADD CONSTRAINT pk_category_id PRIMARY KEY (id);
--------------------------------------------------------------------------------------------------------



---Supplier-------------------------------------------------------------------------Supplier
alter table if exists only public.supplier drop constraint if exists pk_supplier_id cascade;
drop table if exists public.supplier cascade ;
drop sequence if exists public.supplier cascade ;

CREATE TABLE supplier
(
    id int unique,
    name varchar(255)
);
ALTER TABLE ONLY supplier
    ADD CONSTRAINT pk_supplier_id PRIMARY KEY (id);
-----------------------------------------------------------------------------------------------------------------


--Warehouse--------------------------------------------------------------------------------------------
alter table if exists only public.warehouse drop constraint if exists pk_warehouse_id cascade;
drop table if exists public.warehouse cascade ;
drop sequence if exists public.warehouse cascade ;

CREATE  TABLE warehouse
(
    id serial not null,
    product_id int,
    name varchar(255),
    quantity int
);

ALTER TABLE ONLY warehouse
    ADD CONSTRAINT pk_warehouse_id PRIMARY KEY (id);
ALTER TABLE ONLY warehouse
    ADD CONSTRAINT fk_product_id foreign key (product_id) references product(id);
----------------------------------------------------------------------------------------------

--checkout-------------------------------------------------------------------------
alter table if exists only public.checkout drop constraint if exists pk_checkout_id cascade;
drop table if exists public.checkout cascade ;
drop sequence if exists public.checkout cascade ;

CREATE TABLE checkout
(
    id serial not null,
    name varchar(255),
    email varchar(255),
    basket_id int,
    address varchar(255),
    city varchar(255),
    zip int,
    state varchar(255),
    payment_id int

);

ALTER TABLE ONLY checkout
    ADD CONSTRAINT pk_checkout_id PRIMARY KEY (id);
ALTER TABLE ONLY checkout
    ADD CONSTRAINT fk_payment_id foreign key (payment_id) references payment(id);
ALTER TABLE ONLY checkout
    ADD CONSTRAINT fk_basket_id foreign key (basket_id) references basket(id);

---------------------------------------------------------------------------------------------


--payment----------------------------------------------------------------------------------
alter table if exists only public.payment drop constraint if exists pk_payment_id cascade;
drop table if exists public.payment cascade;
drop sequence if exists public.payment cascade;

CREATE TABLE payment
(
    id serial not null,
    name_on_card varchar(255),
    basket_id int,
    credit_card_number text,
    exp_month varchar(255),
    exp_year int,
    cvv int,
    checkout_id int
);

ALTER TABLE ONLY payment
    ADD CONSTRAINT pk_payment_id PRIMARY KEY (id);
ALTER TABLE ONLY payment
    ADD CONSTRAINT fk_checkout_id foreign key (checkout_id) references checkout(id);
ALTER TABLE ONLY payment
    ADD CONSTRAINT fk_basketD_id foreign key (basket_id) references basket(id);
----------------------------------------------------------------------------------------------

--Cart--------------------------------------------------------------------------------
alter table if exists only public.basket drop constraint if exists pk_cart_id cascade;
drop table if exists public.basket cascade;
drop sequence if exists public.basket cascade;

CREATE TABLE basket
(
    id serial not null,
    product_id int[],
    quantity int[],
    regd_user_id int
);
ALTER TABLE ONLY basket
    ADD CONSTRAINT pk_basket_id PRIMARY KEY (id);
ALTER TABLE ONLY basket
    ADD CONSTRAINT fk_regd_user_id foreign key (regd_user_id) references regdCustomer(id);
------------------------------------------------------------------------------------------------






