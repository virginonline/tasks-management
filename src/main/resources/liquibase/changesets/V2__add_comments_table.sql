create table if not exists comments
(
    id         bigserial primary key,
    content    text,
    user_id bigint not null,
    task_id bigint not null,
    created_at timestamptz default now(),
    updated_at timestamptz default now(),
    constraint fk_users_tasks_users foreign key (user_id) references users (id) on delete cascade on update no action,
    constraint fk_users_tasks_tasks foreign key (task_id) references tasks (id) on delete cascade on update no action

);