#### SQL语句命名需要遵从一定的规范，否则运行的时候flyway会报错。命名规则主要有两种：

三种类型：

- Versioned、Repeatable和Undo。其中，Versioned用于版本升级，每个版本有唯一的版本号并只能执行一次；

- Repeatable可重复执行，当Flyway检测到Repeatable类型的SQL脚本的checksum有变动，Flyway就会重新应用该脚本；

- Undo用于撤销上一次的迁移操作。

![Versioned.png](doc%2Fimages%2FVersioned.png)

1、仅需要被执行一次的SQL命名以大写的"V"开头，后面跟上"0~9"数字的组合,数字之间可以用“.”或者下划线"_"分割开，
然后再以两个下划线分割，其后跟文件名称，最后以.sql结尾。
比如，V2.1.5__create_user_ddl.sql、V4.1_2__add_user_dml.sql。

2、可重复运行的SQL，则以大写的“R”开头，后面再以两个下划线分割，其后跟文件名称，最后以.sql结尾。
比如，R__truncate_user_dml.sql。

其中，V开头的SQL执行优先级要比R开头的SQL优先级高。

#### 准备了三个脚本，分别为：

1- V1__create_user.sql ,建立一张user表，且只执行一次。

````
CREATE TABLE IF NOT EXISTS `USER`(
    `USER_ID`          INT(11)           NOT NULL AUTO_INCREMENT,
`USER_NAME`        VARCHAR(100)      NOT NULL COMMENT '用户姓名',
`AGE`              INT(3)            NOT NULL COMMENT '年龄',
`CREATED_TIME`     datetime          NOT NULL DEFAULT CURRENT_TIMESTAMP,
`CREATED_BY`       varchar(100)      NOT NULL DEFAULT 'UNKNOWN',
`UPDATED_TIME`     datetime          NOT NULL DEFAULT CURRENT_TIMESTAMP,
`UPDATED_BY`       varchar(100)      NOT NULL DEFAULT 'UNKNOWN',
PRIMARY KEY (`USER_ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
````

2- V2__add_user.sql，其中代码如下，目的是往user表中插入一条数据，且只执行一次。

````
insert into `userDetails`(user_name,age) values('lisi',33);
````

3- R__add_unknown_user.sql，目的是每次启动倘若有变化，则往user表中插入一条数据。

````
insert into `userDetails`(user_name,age) values('unknown',33);
````

#### 存放结构：

````
resources
-db
--migration
---1.0.0
----V1__create_user.sql
---1.0.1
----V2__add_user.sql
---every
----R__add_unknown_user.sql
````

其中1.0.0、1.0.1和every的文件夹不会影响flyway对SQL的识别和运行，可以自行取名和分类。

flyway的默认配置已经足够我们开始运行了。此时，我们启动SpringBoot

刷新数据库，可以看到flyway的历史记录表已经生成并插入了三个版本的记录

而且，user表也已经创建好了并插入了两条数据

不改变任何东西，再次执行主程序，两张数据库表中的内容也毫无任何变化。

可是，如果我们修改V2__add_user.sql中的内容，再次执行的话，就会报错Migration checksum mismatch for migration version 2

如果我们修改了R__add_unknown_user.sql，再次执行的话，该脚本就会再次得到执行，并且flyway的历史记录表中也会增加本次执行的记录。

#### maven插件的使用

以上步骤中，每次想要migration都需要运行整个springboot项目，并且只能执行migrate一种命令，其实flyway还是有很多其它命令的。maven插件给了我们不需要启动项目就能执行flyway各种命令的机会。

在pom中引入flyway的插件，同时配置好对应的数据库连接。

````
            <plugin>
                <groupId>org.flywaydb</groupId>
                <artifactId>flyway-maven-plugin</artifactId>
                <version>5.2.4</version>
                <configuration>
                    <url>jdbc:mysql://localhost:3306/flyway?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT</url>
                    <userDetails>root</userDetails>
                    <password>root</password>
                    <driver>com.mysql.cj.jdbc.Driver</driver>
                </configuration>
            </plugin>
````

查看maven-plugin中就有flyway的指令了

flyway:migrate的效果和启动整个工程执行migrate的效果是一样的。

其它命令的作用如下列出：

#### baseline

对已经存在数据库Schema结构的数据库一种解决方案。实现在非空数据库新建MetaData表，并把Migrations应用到该数据库；也可以在已有表结构的数据库中实现添加Metadata表。

#### clean

清除掉对应数据库Schema中所有的对象，包括表结构，视图，存储过程等，clean操作在dev 和 test阶段很好用，但在生产环境务必禁用。

#### info

用于打印所有的Migrations的详细和状态信息，也是通过MetaData和Migrations完成的，可以快速定位当前的数据库版本。

#### repair

repair操作能够修复metaData表，该操作在metadata出现错误时很有用。

#### undo

撤销操作，社区版不支持。

#### validate

验证已经apply的Migrations是否有变更，默认开启的，原理是对比MetaData表与本地Migrations的checkNum值，如果值相同则验证通过，否则失败。

#### flyway补充知识

flyway执行migrate必须在空白的数据库上进行，否则报错；
对于已经有数据的数据库，必须先baseline，然后才能migrate；
clean操作是删除数据库的所有内容，包括baseline之前的内容；
尽量不要修改已经执行过的SQL，即便是R开头的可反复执行的SQL，它们会不利于数据迁移；

flyway的配置清单：

````
flyway.baseline-description对执行迁移时基准版本的描述.
flyway.baseline-on-migrate当迁移时发现目标schema非空，而且带有没有元数据的表时，是否自动执行基准迁移，默认false.
flyway.baseline-version开始执行基准迁移时对现有的schema的版本打标签，默认值为1.
flyway.check-location检查迁移脚本的位置是否存在，默认false.
flyway.clean-on-validation-error当发现校验错误时是否自动调用clean，默认false.
flyway.enabled是否开启flywary，默认true.
flyway.encoding设置迁移时的编码，默认UTF-8.
flyway.ignore-failed-future-migration当读取元数据表时是否忽略错误的迁移，默认false.
flyway.init-sqls当初始化好连接时要执行的SQL.
flyway.locations迁移脚本的位置，默认db/migration.
flyway.out-of-order是否允许无序的迁移，默认false.
flyway.password目标数据库的密码.
flyway.placeholder-prefix设置每个placeholder的前缀，默认${.
flyway.placeholder-replacementplaceholders是否要被替换，默认true.
flyway.placeholder-suffix设置每个placeholder的后缀，默认}.
flyway.placeholders.[placeholder name]设置placeholder的value
flyway.schemas设定需要flywary迁移的schema，大小写敏感，默认为连接默认的schema.
flyway.sql-migration-prefix迁移文件的前缀，默认为V.
flyway.sql-migration-separator迁移脚本的文件名分隔符，默认__
flyway.sql-migration-suffix迁移脚本的后缀，默认为.sql
flyway.tableflyway使用的元数据表名，默认为schema_version
flyway.target迁移时使用的目标版本，默认为latest version
flyway.url迁移时使用的JDBC URL，如果没有指定的话，将使用配置的主数据源
flyway.user迁移数据库的用户名
flyway.validate-on-migrate迁移时是否校验，默认为true
````