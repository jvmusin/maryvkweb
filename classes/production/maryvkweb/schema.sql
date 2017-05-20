create table if not exists user(
  id int not null primary key,
  first_name varchar(255) not null,
  last_name varchar(255) not null
);

create table if not exists registered_seeker(
	id bigint not null auto_increment primary key,
	target_id int not null
);

create table if not exists relation(
	id bigint not null auto_increment primary key,
	owner_id int not null,
  target_id int not null,
  relation_type int not null,
  unique key(owner_id, target_id, relation_type)
);

create table if not exists relation_change(
	id bigint not null auto_increment primary key,
	owner_id int not null,
  target_id int not null,
	relation_type int not null,
  is_appeared bit not null,
	time tinyblob not null
);