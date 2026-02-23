CREATE TABLE public.books (
	id bigserial NOT NULL,
	title varchar(255) NOT NULL,
	"year" int4 NULL,
	isbn varchar(20) NULL,
	publisher varchar(255) NULL,
	CONSTRAINT books_pkey PRIMARY KEY (id)
);

CREATE TABLE public.authors (
	id uuid DEFAULT gen_random_uuid() NOT NULL,
	last_name varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	life_years varchar(25) NULL,
	CONSTRAINT authors_pkey PRIMARY KEY (id)
);

CREATE TABLE public.book_authors (
	book_id int8 NOT NULL,
	author_id uuid NOT NULL,
	CONSTRAINT book_authors_pkey PRIMARY KEY (book_id, author_id),
	CONSTRAINT book_authors_author_id_fkey FOREIGN KEY (author_id) REFERENCES public.authors(id),
	CONSTRAINT book_authors_book_id_fkey FOREIGN KEY (book_id) REFERENCES public.books(id)
);

CREATE TABLE public.book_copies (
	id serial4 NOT NULL,
	book_id int8 NULL,
	inventory_number int4 NOT NULL,
	status varchar(25) NULL,
	CONSTRAINT book_copies_pkey PRIMARY KEY (id),
	CONSTRAINT book_copies_book_id_fkey FOREIGN KEY (book_id) REFERENCES public.books(id)
);

CREATE TABLE public.users (
	id serial4 NOT NULL,
	last_name varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	"role" varchar(255) NULL,
	iin int4 NOT NULL,
	address varchar(255) NULL,
	phone varchar(25) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE public.loans (
	id serial4 NOT NULL,
	book_copy_id int4 NOT NULL,
	user_id int4 NOT NULL,
	loan_date date NOT NULL,
	return_date date NULL,
	CONSTRAINT loans_pkey PRIMARY KEY (id),
	CONSTRAINT loans_book_copy_id_fkey FOREIGN KEY (book_copy_id) REFERENCES public.book_copies(id),
	CONSTRAINT loans_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE TABLE public.reader_cards (
	reader_id int4 NOT NULL,
	card_number varchar(20) NOT NULL,
	issue_date date NOT NULL,
	expiration_date date NULL,
	CONSTRAINT reader_cards_card_number_key UNIQUE (card_number),
	CONSTRAINT reader_cards_pkey PRIMARY KEY (reader_id),
	CONSTRAINT reader_cards_reader_id_fkey FOREIGN KEY (reader_id) REFERENCES public.users(id)
);