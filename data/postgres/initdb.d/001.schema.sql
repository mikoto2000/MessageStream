drop table if exists account;

create table account (
  id uuid primary key default uuid_generate_v4(),
  issuer text not null,
  sub text not null
  unique (issuer, sub)
);

