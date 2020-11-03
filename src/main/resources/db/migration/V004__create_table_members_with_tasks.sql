create table members_with_tasks
(
    member_id integer not null
        constraint members_with_tasks_member_id_fkey
            references members
            on update cascade on delete cascade,
    task_id integer not null
        constraint members_with_tasks_task_id_fkey
            references tasks
            on update cascade on delete cascade,
    constraint members_with_tasks_pk
        primary key (member_id, task_id)
);