create table users (
    id uuid primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    email_address varchar(255) not null,

    constraint uniqueEmailAddress unique(email_address)
);
