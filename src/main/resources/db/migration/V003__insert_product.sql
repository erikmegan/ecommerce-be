CREATE TABLE products (
	id int8 NOT NULL,
	created_date timestamp NULL,
	is_deleted bool NULL DEFAULT false,
	updated_date timestamp NULL,
	"version" int8 NULL,
	description varchar(255) NULL,
	is_active bool NULL,
	merchant_id int8 NULL,
	"name" varchar(255) NULL,
	product_type_id int8 NULL,
	stock int4 NULL,
	CONSTRAINT products_pkey PRIMARY KEY (id)
);

INSERT INTO products
(id, created_date, is_deleted, updated_date, "version", description, is_active, merchant_id, "name", product_type_id, stock)
VALUES(1, now(), false, now(), 0, 'brand new Tshirt', true, 1, 'Tshirt', 2, 5);