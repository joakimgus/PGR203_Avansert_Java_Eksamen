create table if not exists status (
id serial constraint status_pk primary key,
status_name varchar(50)
);