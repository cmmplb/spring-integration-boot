DROP DATABASE IF EXISTS `spring_integration`;

CREATE DATABASE `spring_integration` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `spring_integration`;

/*系统日志表*/
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `time`         int(11)       DEFAULT NULL COMMENT '响应时间',
    `method`       varchar(256)  DEFAULT NULL COMMENT '请求方法',
    `params`       text COMMENT '请求参数',
    `ip`           varchar(64)   DEFAULT NULL COMMENT 'ip地址',
    `exc_cause`    varchar(1024) DEFAULT NULL COMMENT '异常原因',
    `exc_desc`     varchar(1024) DEFAULT NULL COMMENT '异常描述',
    `exc_location` varchar(1024) DEFAULT NULL COMMENT '异常位置',
    `create_time`  datetime   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统日志表';

COMMIT;