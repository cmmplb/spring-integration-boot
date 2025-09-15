DROP DATABASE IF EXISTS `spring_boot_log`;

CREATE DATABASE `spring_boot_log` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `spring_boot_log`;

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `status`        tinyint(4) NOT NULL COMMENT '日志状态:0-正常;1-异常;',
    `type`          varchar(128)        DEFAULT NULL COMMENT '操作类型',
    `business_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '业务类型:0-新增;1-删除;2-修改;3-查询;4-导入;5-导出;',
    `content`       varchar(512)        DEFAULT NULL COMMENT '操作内容',
    `ip`            varchar(255)        DEFAULT NULL COMMENT '操作IP地址',
    `user_agent`    varchar(1024)       DEFAULT NULL COMMENT '用户代理',
    `request_uri`   varchar(255)        DEFAULT NULL COMMENT '请求URI',
    `method`        varchar(8)          DEFAULT NULL COMMENT '请求方式',
    `method_name`   varchar(256)        DEFAULT NULL COMMENT '请求方法',
    `params`        varchar(2048)       DEFAULT NULL COMMENT '请求参数',
    `time`          bigint(20)          DEFAULT NULL COMMENT '执行时间(毫秒)',
    `exc_cause`     varchar(2048)       DEFAULT NULL COMMENT '异常原因',
    `exc_desc`      varchar(256)        DEFAULT NULL COMMENT '异常描述',
    `exc_location`  varchar(256)        DEFAULT NULL COMMENT '异常位置',
    `create_time`   datetime   NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统日志表';


COMMIT;

