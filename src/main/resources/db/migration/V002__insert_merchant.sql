CREATE TABLE merchants (
	id int8 NOT NULL,
	created_date timestamp NULL,
	is_deleted bool NULL DEFAULT false,
	updated_date timestamp NULL,
	"version" int8 NULL,
	is_active bool NULL,
	"name" varchar(255) NULL,
	CONSTRAINT merchants_pkey PRIMARY KEY (id)
);

INSERT INTO merchants
(id, created_date, is_deleted, updated_date, "version", is_active, "name")
VALUES(1, now(), false, now(), 0, true, 'toko jaya');