create table addresses (
    id uuid primary key,
    user_id uuid not null,
    street varchar(255) not null,
    zipcode varchar(255) not null,
    city varchar(255) not null,

    constraint fk_user foreign key(user_id) references users(id)
);
