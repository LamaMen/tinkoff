create table user_account
(
    id       uuid not null
        constraint test_pk
            primary key,
    name     varchar(64),
    password varchar(64)
);

alter table user_account
    owner to ilia;

create table money_transaction
(
    id       serial primary key,
    amount   bigint,
    title    varchar(255),
    date     timestamp,
    is_coast boolean,
    user_id uuid
        constraint foreign_key_for_user_account
            references user_account
);

alter table money_transaction
    owner to ilia;
