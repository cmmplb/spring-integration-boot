DROP DATABASE IF EXISTS `spring_boot_mybatis_plus`;

CREATE DATABASE `spring_boot_mybatis_plus` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `spring_boot_mybatis_plus`;

/*用户信息表*/
DROP TABLE IF EXISTS `userDetails`;
CREATE TABLE `userDetails`
(
    `id`          bigint     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_id`   bigint(20) NOT NULL DEFAULT '0' COMMENT '租户id',
    `name`        varchar(32)         DEFAULT NULL COMMENT '用户名',
    `sex`         tinyint             DEFAULT NULL COMMENT '性别:0-女;1-男',
    `mobile`      varchar(16)         DEFAULT NULL COMMENT '手机号',
    `status`      tinyint             DEFAULT '0' COMMENT '用户状态:0-正常;1-禁用',
    `version`     int(11)    NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    `create_time` datetime   NOT NULL COMMENT '创建时间',
    `update_time` datetime            DEFAULT NULL COMMENT '更新时间',
    `deleted`     tinyint    NOT NULL DEFAULT '0' COMMENT '逻辑删除:0-正常;1-删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息表';

INSERT INTO `userDetails` (`tenant_id`,
                    `name`,
                    `sex`,
                    `mobile`,
                    `status`,
                    `create_time`,
                    `update_time`,
                    `version`,
                    `deleted`)
VALUES (1,
        '小明',
        1,
        '13999999999',
        0,
        NOW(),
        NOW(),
        1,
        0),
       (2,
        '小芳',
        0,
        '14999999999',
        1,
        NOW(),
        NOW(),
        1,
        0);

/*用户详情表*/
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `id`          bigint   NOT NULL COMMENT '主键-对应用户信息表id',
    `icon`        varchar(256)       DEFAULT NULL COMMENT '头像',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime          DEFAULT NULL COMMENT '更新时间',
    `deleted`     tinyint  NOT NULL DEFAULT '0' COMMENT '逻辑删除 默认0， 1，删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户详情表';
INSERT INTO `user_info` (`id`,
                         `icon`,
                         `create_time`,
                         `update_time`,
                         `deleted`)
VALUES (1,
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic4.zhimg.com%2Fv2-b6eae3250bb62fadb3d2527f466cf033_b.jpg&refer=http%3A%2F%2Fpic4.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632014610&t=51f4c1e043702758b668c197b4432664',
        NOW(),
        NOW(),
        0),
       (2,
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic4.zhimg.com%2Fv2-b6eae3250bb62fadb3d2527f466cf033_b.jpg&refer=http%3A%2F%2Fpic4.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632014610&t=51f4c1e043702758b668c197b4432664',
        NOW(),
        NOW(),
        0);

/*标签表*/
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(32) NOT NULL COMMENT '标签名称',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `create_by`   bigint(20)  NOT NULL COMMENT '创建人',
    `update_time` datetime    NOT NULL COMMENT '更新时间',
    `update_by`   bigint(20)  NOT NULL COMMENT '更新人',
    `deleted`     tinyint(4)  NOT NULL COMMENT '逻辑删除:0-正常;1-删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='标签表';
INSERT INTO `tag` (`name`,
                   `create_time`,
                   `create_by`,
                   `update_time`,
                   `update_by`,
                   `deleted`)
VALUES ('新用户',
        NOW(),
        1,
        NOW(),
        1,
        0),
       ('老用户',
        NOW(),
        1,
        NOW(),
        1,
        0)
        ,
       ('活跃用户',
        NOW(),
        1,
        NOW(),
        1,
        0);

/*用户标签关联表*/
DROP TABLE IF EXISTS `user_tag`;
CREATE TABLE `user_tag`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     bigint(20) NOT NULL COMMENT '用户id',
    `tag_id`      bigint(20) NOT NULL COMMENT '标签id',
    `create_time` datetime   NOT NULL COMMENT '创建时间',
    `create_by`   bigint(20) NOT NULL COMMENT '创建人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UQ_U_T` (`user_id`, `tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户标签关联表';
INSERT INTO `user_tag` (`user_id`,
                        `tag_id`,
                        `create_time`,
                        `create_by`)
VALUES (1,
        1,
        NOW(),
        1),
       (1,
        2,
        NOW(),
        1),
       (2,
        2,
        NOW(),
        1),
       (2,
        3,
        NOW(),
        1);

COMMIT;