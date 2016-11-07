create table if not exists recipes(
  name varchar2(50) not null,
  description varchar2(50),
  constraint pk_recipe_name primary key (name)
);
create table if not exists ingredients(
  name varchar2(50) not null,
  measure_unit varchar2(50) not null,
  constraint pk_ingredient_name primary key (name)
);
create table if not exists ingredients_of_recipes(
  rname varchar2(50) not null references recipes(name),
  iname varchar2(50) not null references ingredients(name),
  amount integer not null default 0,

  foreign key(rname) references recipes(name) on delete restrict,
  foreign key(iname) references ingredients(name) on delete restrict
);

