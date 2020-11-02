create table membersAssignedTasks
(
    member_id integer not null
        constraint membersAssignedTasks_member_id_fkey
            references members
            on update cascade on delete cascade,
    task_id integer not null
        constraint membersAssignedTasks_task_id_fkey
            references tasks
            on update cascade on delete cascade,
    constraint membersAssignedTasks_pk
        primary key (member_id, task_id)
);