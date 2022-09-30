create table bucket
(
    id bigint not null,
    user_id bigint,
    primary key (id)
);

create table bucket_products
(
    bucket_id bigint not null,
    product_id bigint not null
);

create table bucket_seq (next_val bigint);

insert into bucket_seq values ( 1 );

create table categories
(
    id bigint not null,
     title varchar(255),
      primary key (id)
);

create table category_seq (next_val bigint);

insert into category_seq values ( 1 );

create table orders
(
    id bigint not null,
    address varchar(255),
    created datetime(6),
    status varchar(255),
    sum decimal(19,2),
    user_id bigint,
    primary key (id)
);

create table orders_details
(
    id bigint not null,
    amount decimal(19,2),
    price decimal(19,2),
    order_id bigint,
    product_id bigint,
    details_id bigint not null,
    primary key (id)
);

create table orders_details_seq (next_val bigint);

insert into orders_details_seq values ( 1 );

create table orders_seq (next_val bigint);

insert into orders_seq values ( 1 );

create table product
(
    id bigint not null,
    price decimal(19,2),
    title varchar(255),
    primary key (id)
);

create table product_categories
(
    product_id bigint not null,
    category_id bigint not null
);

create table product_seq (next_val bigint);

insert into product_seq values ( 1 );

create table user_seq (next_val bigint);

insert into user_seq values ( 1 );

create table users
(
    id bigint not null,
    archive bit not null,
    email varchar(255),
    name varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);


alter table orders_details add constraint UK_kk6y3pyhjt6kajomtjbhsoajo unique (details_id);

alter table bucket add constraint FK327w43qqj7sg7250igsyxw7s3 foreign key (user_id) references users (id);

alter table bucket_products add constraint FK3dwp02gip9thr6eec4qyst3r8 foreign key (product_id) references product (id);

alter table bucket_products add constraint FKt70fe5opygmnfsbmmxoai26xs foreign key (bucket_id) references bucket (id);

alter table orders add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users (id);

alter table orders_details add constraint FK5o977kj2vptwo70fu7w7so9fe foreign key (order_id) references orders (id);

alter table orders_details add constraint FKpnhck06jl5as1kxb6fk2jtthq foreign key (product_id) references product (id);

alter table orders_details add constraint FKgvp1k7a3ubdboj3yhnawd5m1p foreign key (details_id) references orders_details (id);

alter table product_categories add constraint FKd112rx0alycddsms029iifrih foreign key (category_id) references categories (id);

alter table product_categories add constraint FKppc5s0f38pgb35a32dlgyhorc foreign key (product_id) references product (id);
