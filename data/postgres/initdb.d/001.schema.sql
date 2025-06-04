CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

drop table if exists mastodon_service;
drop table if exists bluesky_service;
drop table if exists account;

create table account (
  id uuid primary key default uuid_generate_v4(),
  issuer text not null,
  sub text not null,
  unique (issuer, sub)
);

create table bluesky_service (
  id uuid primary key default uuid_generate_v4(),
  account_id uuid not null references account (id),
  url text not null,
  handle text not null,
  app_password text not null,
  access_token text,
  refresh_token text,
  token_expires_at timestamp,
  unique (url, handle)
);

create table mastodon_service (
  id uuid primary key default uuid_generate_v4(),
  account_id uuid not null references account (id),
  url text not null,
  access_token text not null,
  unique (url, access_token)
);

