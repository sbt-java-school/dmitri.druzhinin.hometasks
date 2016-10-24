create table if not exists cache.invokes(
    delegate blob not null,
    method varchar(30) not null,
    args blob not null,
    result blob not null,

    constraint pk_delegate_method_args primary key (delegate(1024), method, args(1024))
);