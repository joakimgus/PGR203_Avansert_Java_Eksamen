create table if not exists members (
    id serial constraint members_pk primary key,
        name varchar(100),
        email varchar(320)
);