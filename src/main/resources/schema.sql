create table if not exists registered_seeker (
	id bigint auto_increment
		primary key,
	connected_id int not null
);

create table if not exists relation(
	id bigint auto_increment
		primary key,
  connected_id int not null,
	target_id int not null,
	relation_type int not null,
	constraint owner_id
		unique (connected_id, target_id, relation_type)
);

create table if not exists relation_change(
	id bigint auto_increment
		primary key,
  time tinyblob not null,
  connected_id int not null,
	target_id int not null,
	relation_type int not null,
	is_appeared bit not null
);

create table if not exists user(
	id int not null
		primary key,
	first_name varchar(255) not null,
	last_name varchar(255) not null
);