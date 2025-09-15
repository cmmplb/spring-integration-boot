BEGIN;
CREATE DATABASE IF NOT EXISTS `spring_boot_logging`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;
COMMIT;

USE `spring_boot_logging`;

BEGIN;
DROP TABLE IF EXISTS `logging_event_property`;
DROP TABLE IF EXISTS `logging_event_exception`;
DROP TABLE IF EXISTS `logging_event`;
COMMIT;

-- logging_event ( 日志事件信息表 )
BEGIN;
CREATE TABLE `logging_event`
(
    `timestmp`          bigint                                  NOT NULL COMMENT '日志事件发生的时间戳',
    `formatted_message` text COLLATE utf8mb4_general_ci         NOT NULL COMMENT '格式化后的日志消息, 实际记录的日志内容',
    `logger_name`       varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志记录器名称, Java 类全限定名',
    `level_string`      varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志级别, 如 DEBUG, INFO, ERROR',
    `thread_name`       varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '线程名称',
    `reference_flag`    smallint                                DEFAULT NULL COMMENT '参考标志, 用于标识日志事件的引用',
    `arg0`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第一个参数',
    `arg1`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第二个参数',
    `arg2`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第三个参数',
    `arg3`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第四个参数',
    `caller_filename`   varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用日志记录文件名',
    `caller_class`      varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用日志记录类全限定名',
    `caller_method`     varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用日志记录方法名',
    `caller_line`       char(4) COLLATE utf8mb4_general_ci      NOT NULL COMMENT '调用日志记录代码行号',
    `event_id`          bigint                                  NOT NULL AUTO_INCREMENT COMMENT '主键, 日志事件的唯一标识符',
    PRIMARY KEY (`event_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='日志事件信息表';
COMMIT;

-- logging_event_property ( 日志事件额外属性信息表 )
BEGIN;
CREATE TABLE `logging_event_property`
(
    `event_id`     bigint                                  NOT NULL COMMENT '外键, 引用 logging_event 表的 event_id',
    `mapped_key`   varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '映射的键, 用于标识额外属性的名称',
    `mapped_value` text COLLATE utf8mb4_general_ci         NOT NULL COMMENT '映射的值, 额外属性的值',
    PRIMARY KEY (`event_id`, `mapped_key`),
    CONSTRAINT `logging_event_property_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='日志事件额外属性信息表';
COMMIT;

-- logging_event_exception ( 日志事件异常信息表 )
BEGIN;
CREATE TABLE `logging_event_exception`
(
    `event_id`   bigint                                  NOT NULL COMMENT '外键, 引用 logging_event 表的 event_id',
    `i`          smallint                                NOT NULL COMMENT '异常信息的索引, 堆栈信息的行号',
    `trace_line` varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '异常堆栈信息的具体行内容',
    PRIMARY KEY (`event_id`, `i`),
    CONSTRAINT `logging_event_exception_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='日志事件异常信息表';
COMMIT;