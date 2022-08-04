学习mongoDB：
https://mrbird.cc/MongoDB-shell.html

###Mongo DB下载地址：https://www.mongodb.com/download

####bin目录配置到系统环境，创建一个数据库保存目录，比如D:\MongoDB，然后打开命令窗口输入如下命令启动MongoDB服务：

````
mongod --dbpath=D:\ProgramFileDev\MongoDB\Data
````

打开另外一个命令窗口作为客户端，输入mongo即可连上服务：
````
mongo
````

####语法糖
MongoDB shell自带了一些语法糖：
````
show dbs     列出所有DB
use dbname   切换当前DB
show tables  或 show collections  列出当前DB的所有表/集合
show users   列出当前DB的所有用户
show profile 列出当前DB的所有慢查询
show logs     列出运行日志

docment对应关系型数据库table
删除：
db.docment.drop()
新增：
db.docment.insert(
{"name":"张三"}
)
删除满足后续条件的数据
db.docment.remove({"name":"mrbird"})
查询列表-可添加条件find({"name":"张三"})
db.docment.find()
查询单个
db.docment.findOne()
db.springboot_mongodb.insert({"name":"张三"});
````

####使用shell执行脚本
test.js
print("hello MnogoDB")
有两种方式让MongoDB执行该脚本：
连上服务前
````
mongo test.js
````
连上服务后
连上服务后，可使用load()函数执行脚本：
````
load("test.js")
hello MnogoDB
true
````

mongo 成功连接到 MongoDB 时将检查用户的主目录中是否有一个名为 .mongorc.js 的 JavaScript 文件。如果找到，mongo 将在第一次显示提示符之前执行 .mongorc.js 的内容。

===============================================================

===============================================================

===============================================================

##相关语法：
####3.3.1 选择和创建数据库

选择和创建数据库的语法格式：

```bash
use 数据库名称
```


如果数据库存在则选择该数据库，如果数据库不存在则自动创建。以下语句创建commentdb数据库：

```bash
use commentdb
```



查看数据库：

```bash
show dbs
```



查看集合,需要先选择数据库之后，才能查看该数据库的集合：

```bash
show collections
```



#### 3.3.2 插入与查询文档

选择数据库后，使用集合来对文档进行操作，插入文档语法格式：

```bash
db.集合名称.insert(数据);
```

插入以下测试数据：

```bash
db.comment.insert({content:"十次方课程",userid:"1011"})
```



查询集合的语法格式：

```bash
db.集合名称.find()
```

查询spit集合的所有文档，输入以下命令：

```bash
db.comment.find()
```

​	发现文档会有一个叫_id的字段，这个相当于我们原来关系数据库中表的主键，当你在插入文档记录时没有指定该字段，MongoDB会自动创建，其类型是ObjectID类型。如果我们在插入文档记录时指定该字段也可以，其类型可以是ObjectID类型，也可以是MongoDB支持的任意类型。



输入以下测试语句:

```bash
db.comment.insert({_id:"1",content:"到底为啥出错",userid:"1012",thumbup:2020});
db.comment.insert({_id:"2",content:"加班到半夜",userid:"1013",thumbup:1023});
db.comment.insert({_id:"3",content:"手机流量超了咋办",userid:"1013",thumbup:111});
db.comment.insert({_id:"4",content:"坚持就是胜利",userid:"1014",thumbup:1223});
db.comment.insert({_id:"5",content:"手机没电了啊",userid:"1014",thumbup:923});
db.comment.insert({_id:"6",content:"这个手机好",userid:"1014",thumbup:123});
```



按一定条件来查询，比如查询userid为1013的记录，只要在find()中添加参数即可，参数也是json格式，如下：

```bash
db.comment.find({userid:'1013'})
```

只需要返回符合条件的第一条数据，我们可以使用findOne命令来实现：

```bash
db.comment.findOne({userid:'1013'})
```

返回指定条数的记录，可以在find方法后调用limit来返回结果，例如：

```bash
db.comment.find().limit(2)
```



#### 3.3.3 修改与删除文档

修改文档的语法结构：

```java
db.集合名称.update(条件,修改后的数据)
```

修改_id为1的记录，点赞数为1000，输入以下语句：

```bash
db.comment.update({_id:"1"},{thumbup:1000})
```

执行后发现，这条文档除了thumbup字段其它字段都不见了。

为了解决这个问题，我们需要使用修改器$set来实现，命令如下：

```java
db.comment.update({_id:"2"},{$set:{thumbup:2000}})
```

删除文档的语法结构：

```bash
db.集合名称.remove(条件)
```

以下语句可以将数据全部删除，慎用~

```bash
db.comment.remove({})
```

删除条件可以放到大括号中，例如删除thumbup为1000的数据，输入以下语句：

```bash
db.comment.remove({thumbup:1000})
```



####3.3.4 统计条数

统计记录条件使用count()方法。以下语句统计spit集合的记录数：

```bash
db.comment.count()
```

按条件统计 ，例如统计userid为1013的记录条数：

```bash
db.comment.count({userid:"1013"})
```



#### 3.3.5 模糊查询

MongoDB的模糊查询是通过正则表达式的方式实现的。格式为：

```bash
/模糊查询字符串/
```

查询评论内容包含“流量”的所有文档，代码如下：

```bash
db.comment.find({content:/流量/})
```

查询评论内容中以“加班”开头的，代码如下：

```BASH
db.comment.find({content:/^加班/})
```



#### 3.3.6 大于 小于 不等于

<, <=, >, >= 这个操作符也是很常用的，格式如下:

```bash
db.集合名称.find({ "field" : { $gt: value }}) // 大于: field > value
db.集合名称.find({ "field" : { $lt: value }}) // 小于: field < value
db.集合名称.find({ "field" : { $gte: value }}) // 大于等于: field >= value
db.集合名称.find({ "field" : { $lte: value }}) // 小于等于: field <= value
db.集合名称.find({ "field" : { $ne: value }}) // 不等于: field != value
```

查询评论点赞数大于1000的记录：

```bash
db.comment.find({thumbup:{$gt:1000}})
```



####3.3.7 包含与不包含

包含使用$in操作符

查询评论集合中userid字段包含1013和1014的文档：

```bash
db.comment.find({userid:{$in:["1013","1014"]}})
```



不包含使用$nin操作符

查询评论集合中userid字段不包含1013和1014的文档：

```bash
db.comment.find({userid:{$nin:["1013","1014"]}})
```



#### 3.3.8 条件连接 

我们如果需要查询同时满足两个以上条件，需要使用$and操作符将条件进行关联（相当于SQL的and）。格式为：

```bash
$and:[ {条件},{条件},{条件} ]
```

查询评论集合中thumbup大于等于1000 并且小于2000的文档：

```bash
db.comment.find({$and:[ {thumbup:{$gte:1000}} ,{thumbup:{$lt:2000} }]})
```



如果两个以上条件之间是或者的关系，我们使用操作符进行关联，与前面and的使用方式相同，格式为：

```bash
$or:[ {条件},{条件},{条件} ]
```

查询评论集合中userid为1013，或者点赞数小于2000的文档记录：

```bash
db.comment.find({$or:[ {userid:"1013"} ,{thumbup:{$lt:2000} }]})
```



#### 3.3.9 列值增长 

对某列值在原有值的基础上进行增加或减少，可以使用$inc运算符：

```bash
db.comment.update({_id:"2"},{$inc:{thumbup:1}})
```
