CREATE TABLE IF NOT EXISTS users (
	id integer PRIMARY KEY AUTOINCREMENT,
	username text NOT NULL UNIQUE,
	first_name text NOT NULL,
	last_name text NOT NULL,
	email text NOT NULl UNIQUE,
	identity_num text NOT NULL UNIQUE,
	password text NOT NULL,
	admin numeric NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS categories (
	id integer PRIMARY KEY AUTOINCREMENT,
	name text NOT NULL UNIQUE,
	description text
)

CREATE TABLE IF NOT EXISTS suppliers (
	id integer PRIMARY KEY AUTOINCREMENT,
	name text NOT NULL UNIQUE,
	email text NOT NULL UNIQUE,
	phone text NOT NULL UNIQUE
)

CREATE TABLE IF NOT EXISTS groceries (
	id integer PRIMARY KEY AUTOINCREMENT,
	name text NOT NULL UNIQUE,
	image text,
	description text,
	quantity integer NOT NULL DEFAULT 10,
	category_id integer,
	supplier_id integer,
	FOREIGN KEY(category_id)
		REFERENCES categories (id)
			ON DELETE CASCADE
			ON UPDATE NO ACTION,
	FOREIGN KEY(supplier_id)
		REFERENCES suppliers (id)
			ON DELETE CASCADE
			ON UPDATE NO ACTION
)