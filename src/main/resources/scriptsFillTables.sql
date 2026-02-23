-- Fill in the table Books
insert into books (title, year, isbn, publisher)
values
('Преступление и наказание', '2020', '978-5-699-12014-7', 'Эксмо'),
('Война и мир', '2021', '978-5-238-16112-4', 'ЮНИТИ'),
('Мертвые души', '2020', '978-5-695-10371-5', 'Текст'),
('Отцы и дети', '2023', '978-5-699-15383-3', 'Эксмо'),
('Герой нашего времени', '2024', '978-5-695-22251-6', 'Текст'),
('Тихий Дон', '2021', '978-5-238-15987-1', 'ЮНИТИ'),
('Анна Каренина', '2020', '978-5-699-12638-4', 'Эксмо'),
('Идиот', '2022', '978-5-695-20601-8', 'Текст');


-- Fill in the table Authors
INSERT INTO public.authors
(id, last_name, first_name, life_years)
VALUES('9789df5e-1140-449c-b08d-dff6f9fd1407'::uuid, 'Достоевский', 'Федор', '1821-1881');

INSERT INTO public.authors
(id, last_name, first_name, life_years)
VALUES('6d568ab2-6c43-4b17-99b0-c8d422a46e00'::uuid, 'Толстой', 'Лев', '1828-1910');

INSERT INTO public.authors
(id, last_name, first_name, life_years)
VALUES('4090507d-2566-42fc-96ef-888e392bb287'::uuid, 'Гоголь', 'Николай', '1809-1852');

INSERT INTO public.authors
(id, last_name, first_name, life_years)
VALUES('48857d7e-5045-4582-9cc4-aa59f79137ba'::uuid, 'Тургенев', 'Иван', '1818-1883');

INSERT INTO public.authors
(id, last_name, first_name, life_years)
VALUES('80b1810f-f0c2-4563-bdb7-b1bd9f357cfa'::uuid, 'Лермонтов', 'Михаил', '1814-1841');

INSERT INTO public.authors
(id, last_name, first_name, life_years)
VALUES('084227b2-5314-4a4a-97dd-152ab04264c9'::uuid, 'Шолохов', 'Михаил', '1905-1984');


-- Fill in the table Book_authors
insert into book_authors
select b.id, a.id from books b
join authors a on a.last_name = 'Достоевский'
where b.title = 'Преступление и наказание';

insert into book_authors
select b.id, a.id from books b
join authors a on a.last_name = 'Толстой'
where b.title in ('Война и мир', 'Анна Каренина');

insert into book_authors
select b.id, a.id from books b
join authors a on a.last_name = 'Гоголь'
where b.title = 'Мертвые души';

insert into book_authors
select b.id, a.id from books b
join authors a on a.last_name = 'Тургенев'
where b.title = 'Отцы и дети';

insert into book_authors
select b.id, a.id from books b
join authors a on a.last_name = 'Лермонтов'
where b.title = 'Герой нашего времени';

insert into book_authors
select b.id, a.id from books b
join authors a on a.last_name = 'Шолохов'
where b.title = 'Тихий Дон';

insert into book_authors
select b.id, a.id from books b
join authors a on a.last_name = 'Достоевский'
where b.title = 'Идиот';


-- Fill in the table Book_copies
INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(1, 1, 1001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(2, 2, 2001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(3, 2, 2002, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(4, 2, 2003, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(5, 3, 3001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(6, 4, 4001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(7, 4, 4002, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(8, 5, 5001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(9, 5, 5002, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(10, 5, 5003, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(11, 6, 6001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(12, 6, 6002, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(13, 7, 7001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(14, 7, 7002, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(15, 7, 7003, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(16, 8, 8001, 'available');

INSERT INTO public.book_copies
(id, book_id, inventory_number, status)
VALUES(17, 8, 8002, 'available');