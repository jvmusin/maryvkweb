create table if not exists registered_seeker(
	id int auto_increment primary key,
	connected_id int not null
);

create table if not exists relation(
	id int auto_increment primary key,
  connected_id int not null,
	target_id int not null,
	relation_type int not null
);

create table if not exists relation_change(
	id int auto_increment primary key,
  time DATETIME not null,
  connected_id int not null,
	target_id int not null,
	relation_type int not null,
	is_appeared bit not null
);