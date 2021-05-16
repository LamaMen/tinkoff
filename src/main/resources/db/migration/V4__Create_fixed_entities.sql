create table transactions
(
    id          integer NOT NULL DEFAULT nextval('money_transaction_id_seq') primary key,
    amount      bigint,
    title       varchar(255),
    is_coast    boolean,
    user_id     uuid
        constraint user_account_fk
            references user_account,
    category_id integer          default 0,
    currency    varchar(3)       default 'EUR' not null
);

INSERT INTO transactions(id, amount, title, is_coast, user_id, category_id, currency)
SELECT ID, AMOUNT, TITLE, IS_COAST, USER_ID, CATEGORY_ID, CURRENCY
FROM money_transaction;

create table ordinary_transaction
(
    id   integer primary key
        constraint ordinary_transaction_fk
            references transactions,
    date timestamp
);

INSERT INTO ordinary_transaction(id, date)
SELECT ID, DATE
FROM money_transaction;

create table fixed_transaction
(
    id  integer primary key
        constraint ordinary_transaction_fk
            references transactions,
    day integer
);