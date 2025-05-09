开启binlog日志

https://blog.csdn.net/weixin_44251036/article/details/115077495

创建canal数据库用户
修改mysql配置文件my.cnf加入如下信息
[mysqld]  
log-bin=mysql-bin
binlog-format=ROW
server_id=1

如果是mysql在docker里面
docker exec -it mysql /bin/bash
cd /etc/mysql/mysql.conf.d
vi mysqld.cnf
修改mysqld.cnf配置文件, 添加如下配置：
log-bin=/var/lib/mysql/mysql-bin
server-id=12345 #不能和mysql的重复
重启

查看binlog日志是否开启
#查看是否打开binlog
show variables like 'log_bin';
#查看binlog日志列表
show master status;
#查看当前正在写入的binlog文件
show master status;

添加canal的mysql账号
mysql -uroot -proot
#创建账号(账号：canal;密码：canal）
CREATE USER canal IDENTIFIED BY 'canal'; 
#授予权限
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
#刷新并应用权限
FLUSH PRIVILEGES;

如果为mysql8则用下面方法添加

mysql -uroot -proot
#创建账号(账号：canal;密码：canal）
CREATE USER 'canal'@'%' IDENTIFIED BY 'canal'; 
#因为mysql8不支持GRANT创建用户, 改用CREATE USER, 然后再使用GRANT。
CREATE USER 'canal'@'%' IDENTIFIED BY 'canal'; 
GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' WITH GRANT OPTION;
#因为mysql8修改了加密规则,  低版本的mysql界面工具可能连不上mysql8, 需要更改加密规则。
# 2. 修改加密规则： 
ALTER USER ‘root'@‘localhost' IDENTIFIED BY ‘password' PASSWORD EXPIRE NEVER;
#3更新一下用户的密码 ： 
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password'
#刷新并应用权限
FLUSH PRIVILEGES;


