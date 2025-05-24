drop table if exists account;

create table account (
  id uuid primary key,
  issuer text not null,
  sub text not null,
  display_name TEXT,
  unique (issuer, sub)
);

