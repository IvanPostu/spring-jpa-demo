create table public."users"(
	id int primary key not null,
	name varchar(50),
	created_at TIMESTAMP default now()
);


INSERT INTO public."users"
(id, "name")
VALUES(0, 'jean');
