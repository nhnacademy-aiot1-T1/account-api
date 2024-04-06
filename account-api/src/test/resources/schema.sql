drop table if exists user_auth;
drop table if exists user;

create table user
(
    id        bigint auto_increment,
    auth_type varchar(20) NOT NULL,
    name      varchar(20),
    email     varchar(100),
    status varchar(11) default 'ACTIVE',
    role varchar(5) default 'USER',
    constraint pk_user primary key (id)
);

create table user_auth
(
    id       bigint auto_increment,
    user_id  varchar(20) NOT NULL ,
    password varchar(100) NOT NULL ,
    constraint pk_userauth primary key (id),
    constraint fk_userauth_user foreign key (id) references user (id) on delete cascade
);