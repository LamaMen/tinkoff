create table category
(
    id   serial primary key,
    name varchar(64)
);

INSERT INTO category (id, name)
values (0, null);

ALTER TABLE money_transaction
    ADD COLUMN category_id INTEGER DEFAULT 0;
ALTER TABLE money_transaction
    ADD CONSTRAINT category_fk FOREIGN KEY (category_id) REFERENCES category (id) NOT VALID;