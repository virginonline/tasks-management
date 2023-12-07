create table if not exists comments
(
    id         bigserial primary key,
    content    text,
    created_at timestamptz default now(),
    updated_at timestamptz default now()
);
create table if not exists users_comments
(
    task_id   bigint,
    author_id bigint,
    primary key (task_id, author_id),
    constraint fk_users_comments_users foreign key (author_id) references users (id),
    constraint fk_users_comments_tasks foreign key (task_id) references tasks (id)
)