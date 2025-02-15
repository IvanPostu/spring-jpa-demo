-- CREATE DATABASE first_db1;
-- \c first_db1

DROP TABLE IF EXISTS person;

CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(256),
    age INTEGER,
    email VARCHAR(256)
);

INSERT INTO person (id, name, age, email)
VALUES
(1, 'Bob1', 18, 'bobbob1@mail.com'),
(2, 'Bob2', 19, 'bobbob2@mail.com'),
(3, 'Bob3', 21, 'bobbob3@mail.com'),
(4, 'Bob4', 22, 'bobbob4@mail.com');

SELECT setval('person_id_seq', 999, true);
