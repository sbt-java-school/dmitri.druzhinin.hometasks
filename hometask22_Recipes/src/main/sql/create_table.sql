#create schema if not exists recipes_database;

create table if not exists recipes(
	name varchar(20) not null,
  description varchar(50),

  constraint pk_name primary key (name)
);
create table if not exists ingredients(
	name varchar(20) not null,
  measure_unit varchar(20) not null,

  constraint pk_name primary key (name)
);
create table if not exists ingredients_of_recipes(
	rname varchar(20) not null references recipes(name),
  iname varchar(20) not null references ingredients(name),
  amount integer not null default 0,

  constraint fk_rname foreign key(rname) references recipes(name) on delete restrict,
  constraint fk_iname foreign key(iname) references ingredients(name) on delete restrict
);
