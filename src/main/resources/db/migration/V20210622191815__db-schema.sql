create table users
(
    id       bigserial primary key,
    username varchar(64) unique not null,
    password varchar(64)        not null,
    role     varchar(64)        not null,
    email    varchar(64) unique not null,
    status   varchar(64)        not null

);

create table items
(
    id          bigserial primary key,
    name        varchar(64) unique not null,
    description varchar(1024)       not null
);

create table tags
(
    id   bigserial primary key,
    name varchar(64) unique not null
);

create table item_tags
(
    item_ref bigint references items (id),
    tag_ref  bigint references tags (id),
    unique (item_ref, tag_ref)
);

create table carts
(
    id       bigserial primary key,
    user_ref bigint references users (id)
);

create table cart_items
(
    cart_ref bigint references carts (id),
    item_ref bigint references items (id),
    unique (cart_ref, item_ref)
);
