create table addresses (
    id uuid primary key,
    street varchar(255) not null,
    zipcode varchar(10) not null,
    city varchar(255) not null,
    user_id uuid not null,

    constraint fk_user foreign key(user_id) references users(id)
);
