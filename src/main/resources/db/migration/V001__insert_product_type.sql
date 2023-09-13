CREATE TABLE product_type (
	id int8 NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT product_type_pkey PRIMARY KEY (id)
);

INSERT INTO product_type (id, name)
VALUES (1, 'toy'), (2, 'fashion');