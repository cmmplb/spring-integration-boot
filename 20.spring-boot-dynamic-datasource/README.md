学习dynamic-datasource
文档地址：https://www.kancloud.cn/tracy5546/dynamic-datasource

orcal建表语句：
````
CREATE TABLE t_qr_decode_record
(
    id          NUMBER not null primary key,
    name        VARCHAR2(512),
    md5         VARCHAR2(32),
    type        VARCHAR2(32),
    url         VARCHAR2(512),
    user_agent  VARCHAR2(1024),
    time        NUMBER(20),
    code        VARCHAR2(255),
    file_size      NUMBER(20),
    create_time DATE,
    create_by   VARCHAR2(200),
    update_time DATE,
    update_by   VARCHAR2(200),
    del_flag     NUMBER(1)
);

-- Add comments to the table
comment on table T_QR_DECODE_RECORD is '二维码解析记录表';
-- Add comments to the columns
comment on column T_QR_DECODE_RECORD.id is '主键';
comment on column T_QR_DECODE_RECORD.name is '名称';
comment on column T_QR_DECODE_RECORD.md5 is '文件md5';
comment on column T_QR_DECODE_RECORD.type is '附件类型：如.jpg/.png等';
comment on column T_QR_DECODE_RECORD.user_agent is '用户代理';
comment on column T_QR_DECODE_RECORD.time is '解析耗时(毫秒)';
comment on column T_QR_DECODE_RECORD.code is '解析二维码编码';
comment on column T_QR_DECODE_RECORD.file_size is '文件大小';
comment on column T_QR_DECODE_RECORD.create_time is '创建时间';
comment on column T_QR_DECODE_RECORD.create_by is '创建人';
comment on column T_QR_DECODE_RECORD.update_time is '更新时间';
comment on column T_QR_DECODE_RECORD.update_by is '更新人';
comment on column T_QR_DECODE_RECORD.del_flag is '删除标志0：正常；1：删除；';

create sequence t_qr_decode_record_seq
    minvalue 1
    maxvalue 9999999999999999
    start with 1
    increment by 1
    cache 10;
````