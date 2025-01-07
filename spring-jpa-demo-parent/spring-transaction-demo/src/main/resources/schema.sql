drop table if exists t_account;

create table t_account (
                      id int not null,
                      c_balance int not null default 0 check (c_balance >= 0),
                      primary key (id)
) engine=InnoDB;

insert into t_account
values (1, 550);
