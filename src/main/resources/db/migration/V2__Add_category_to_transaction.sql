create table category
(
    id   integer primary key,
    name varchar(64)
);

INSERT INTO category (id, name)
values (1, 'Home'),
       (2, 'Car'),
       (3, 'Health'),
       (4, 'Personal'),
       (5, 'Clothing'),
       (6, 'Food'),
       (7, 'Presents'),
       (8, 'Family expenses'),
       (9, 'Technics'),
       (10, 'Other');

ALTER TABLE money_transaction
    ADD COLUMN category_id INTEGER DEFAULT 10;
ALTER TABLE money_transaction
    ADD CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES category (id) NOT VALID;