create table if not exists users
(
    id         bigserial primary key,
    username   varchar(255) not null unique,
    password   varchar(255) not null,
    email      varchar(255) not null unique,
    created_at timestamptz default NOW(),
    updated_at timestamptz default NOW()
);

create table if not exists tasks
(
    id          bigserial primary key,
    title       varchar(255) not null,
    description varchar(255),
    status      varchar(255) not null,
    priority    varchar(255) not null,
    created_at  timestamptz default NOW(),
    updated_at  timestamptz default NOW()
);


create table if not exists users_tasks
(
    task_id bigint not null,
    user_id bigint not null,
    primary key (task_id, user_id),
    constraint fk_users_tasks_users foreign key (user_id) references users (id) on delete cascade on update no action,
    constraint fk_users_tasks_tasks foreign key (task_id) references tasks (id) on delete cascade on update no action
);

create table if not exists assigned_tasks
(
    task_id bigint not null,
    user_id bigint not null,
    primary key (task_id, user_id),
    constraint fk_users_tasks_users foreign key (user_id) references users (id) on delete cascade on update no action,
    constraint fk_users_tasks_tasks foreign key (task_id) references tasks (id) on delete cascade on update no action

);

create table if not exists users_roles
(
    user_id BIGINT,
    role    VARCHAR(255) NOT NULL,
    primary key (user_id, role),
    constraint fk_users_roles_users foreign key (user_id) references users (id) on delete cascade on update no action
);
