/*初始化脚本*/

create database if not exists `sharding_sphere_0` default character set utf8mb4 collate utf8mb4_general_ci;

set names utf8mb4;
set foreign_key_checks = 0;

use `sharding_sphere_0`;

drop table if exists `user_0`;
create table `user_0`
(
    `id`          bigint(20)  not null auto_increment comment '主键',
    `name`        varchar(32) not null comment '名称',
    `create_time` datetime    not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

drop table if exists `user_1`;
create table `user_1`
(
    `id`          bigint(20)  not null auto_increment comment '主键',
    `name`        varchar(32) not null comment '名称',
    `create_time` datetime    not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

insert into `sharding_sphere_0`.`user_0` (`id`, `name`, `create_time`)
values (1, '一', now());
insert into `sharding_sphere_0`.`user_0` (`id`, `name`, `create_time`)
values (2, '二', now());
insert into `sharding_sphere_0`.`user_1` (`id`, `name`, `create_time`)
values (3, '三', now());
insert into `sharding_sphere_0`.`user_1` (`id`, `name`, `create_time`)
values (4, '四', now());

/*===================================================================================================================*/

create database if not exists `sharding_sphere_1` default character set utf8mb4 collate utf8mb4_general_ci;

set names utf8mb4;
set foreign_key_checks = 0;

use `sharding_sphere_1`;

drop table if exists `user_0`;
create table `user_0`
(
    `id`          bigint(20)  not null auto_increment comment '主键',
    `name`        varchar(32) not null comment '名称',
    `create_time` datetime    not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

drop table if exists `user_1`;
create table `user_1`
(
    `id`          bigint(20)  not null auto_increment comment '主键',
    `name`        varchar(32) not null comment '名称',
    `create_time` datetime    not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

insert into `sharding_sphere_1`.`user_0` (`id`, `name`, `create_time`)
values (5, '五', now());
insert into `sharding_sphere_1`.`user_0` (`id`, `name`, `create_time`)
values (6, '六', now());
insert into `sharding_sphere_1`.`user_1` (`id`, `name`, `create_time`)
values (7, '七', now());
insert into `sharding_sphere_1`.`user_1` (`id`, `name`, `create_time`)
values (8, '八', now());

/*===================================================================================================================*/

create database if not exists `sharding_sphere_2` default character set utf8mb4 collate utf8mb4_general_ci;

set names utf8mb4;
set foreign_key_checks = 0;

use `sharding_sphere_2`;

drop table if exists `user_0`;
create table `user_0`
(
    `id`          bigint(20)  not null auto_increment comment '主键',
    `name`        varchar(32) not null comment '名称',
    `create_time` datetime    not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

drop table if exists `user_1`;
create table `user_1`
(
    `id`          bigint(20)  not null auto_increment comment '主键',
    `name`        varchar(32) not null comment '名称',
    `create_time` datetime    not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

insert into `sharding_sphere_2`.`user_0` (`id`, `name`, `create_time`)
values (9, '九', now());
insert into `sharding_sphere_2`.`user_0` (`id`, `name`, `create_time`)
values (10, '十', now());
insert into `sharding_sphere_2`.`user_1` (`id`, `name`, `create_time`)
values (11, '十一', now());
insert into `sharding_sphere_2`.`user_1` (`id`, `name`, `create_time`)
values (12, '十二', now());

commit;