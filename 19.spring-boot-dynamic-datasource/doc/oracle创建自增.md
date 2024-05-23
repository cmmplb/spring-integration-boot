首先创建序列

create sequence auto_increment_id(自定义序列名)
start with 10000
increment by 1

在需要使用自增的表上创建触发器

create or replace trigger auto_increment_user(自定义触发器名称)
before insert
on person
for each row
begin
  select auto_increment_id(之前定义的序列名).nextval into :new.pid(需要自增的字段) from dual;
  end;


create sequence auto_increment_id minvalue 1 maxvalue 99999999
start with 10000
increment by 1;

create or replace trigger auto_increment_user
    before insert
    on SYSTEM.USER
    for each row
begin
    select auto_increment_id.nextval into :new.ID from dual;
end;