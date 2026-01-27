CREATE SCHEMA IF NOT EXISTS tabby;

CREATE EXTENSION citext;

CREATE TABLE tabby.user (
    id BIGSERIAL PRIMARY KEY,
    username varchar(20) NOT NULL UNIQUE,
    email citext NOT NULL UNIQUE,
    first_name text NOT NULL,
    last_name text NOT NULL
);

CREATE TABLE tabby.group (
    id BIGSERIAL PRIMARY KEY,
    description text NOT NULL
);

CREATE TABLE tabby.group_user (
    group_id bigint NOT NULL REFERENCES tabby.group(id),
    user_id bigint NOT NULL REFERENCES tabby.user(id),
    admin boolean NOT NULL DEFAULT false,
    PRIMARY KEY (group_id, user_id)
);

CREATE TABLE tabby.category (
    id BIGSERIAL PRIMARY KEY,
    name varchar(20) NOT NULL
);

CREATE TABLE tabby.transaction (
    id BIGSERIAL PRIMARY KEY,
    paid_by bigint NOT NULL REFERENCES tabby.user(id),
    cost money NOT NULL,
    date timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description text NOT NULL,
    notes text,
    group_id bigint REFERENCES tabby.group(id),
    category_id bigint REFERENCES tabby.category(id)
);

CREATE TABLE tabby.split (
    user_id bigint NOT NULL REFERENCES tabby.user(id),
    transaction_id bigint NOT NULL REFERENCES tabby.transaction(id),
    settled boolean NOT NULL DEFAULT false,
    PRIMARY KEY (user_id, transaction_id)
);
