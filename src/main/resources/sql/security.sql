create table t_user(
	f_id int(11) not null auto_increment,
	f_name varchar(32) DEFAULT NULL COMMENT '姓名',
	f_phone char(11) DEFAULT NULL COMMENT '手机号码',
	f_remark varchar(255) DEFAULT NULL,
	f_username varchar(255) DEFAULT NULL COMMENT '用户名',
	f_password varchar(255) DEFAULT NULL COMMENT '密码',
	f_enabled tinyint(1) DEFAULT '1',
	PRIMARY KEY (f_id),
	unique (f_username)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_role(
	f_id int(11) not null auto_increment,
	f_name varchar(64) DEFAULT NULL,
	f_name_zh varchar(64) DEFAULT NULL COMMENT '角色名称',
	PRIMARY KEY (f_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_menu(
	f_id int(11) not null auto_increment,
	f_url varchar(64) DEFAULT NULL,
	f_name varchar(64) DEFAULT NULL,
	PRIMARY KEY (f_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table t_user_role(
	f_id int(11) not null auto_increment,
	f_user_id int(11) DEFAULT NULL,
	f_role_id int(11) DEFAULT NULL,
	PRIMARY KEY (f_id),
	CONSTRAINT fk_ur_user_id FOREIGN KEY (f_user_id) REFERENCES t_user (f_id) ON DELETE CASCADE,
  CONSTRAINT fk_ur_role_id FOREIGN KEY (f_role_id) REFERENCES t_role (f_id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_menu_role(
	f_id int(11) not null auto_increment,
	f_menu_id int(11) DEFAULT NULL,
	f_role_id int(11) DEFAULT NULL,
	PRIMARY KEY (f_id),
	CONSTRAINT fk_mr_menu_id FOREIGN KEY (f_menu_id) REFERENCES t_menu (f_id) ON DELETE CASCADE,
  CONSTRAINT fk_mr_role_id FOREIGN KEY (f_role_id) REFERENCES t_role (f_id) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;