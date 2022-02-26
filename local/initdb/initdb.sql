create user "bank-user" password 'bank-pass';
create database "bank";
create database "bank-test";

grant connect on database "bank-test" to "bank-user";
grant connect on database "bank" to "bank-user";

\c "bank"

grant all privileges on all tables in schema public to "bank-user";
grant all privileges on all sequences in schema public to "bank-user";
grant all privileges on all functions in schema public to "bank-user";

create extension if not exists "uuid-ossp";

\c "bank-test"

grant all privileges on all tables in schema public to "bank-user";
grant all privileges on all sequences in schema public to "bank-user";
grant all privileges on all functions in schema public to "bank-user";

create extension if not exists "uuid-ossp";