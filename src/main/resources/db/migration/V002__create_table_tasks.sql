create table if not exists tasks (
id serial constraint tasks_pk primary key,
title varchar(50),
description varchar(320),
status_id int
);