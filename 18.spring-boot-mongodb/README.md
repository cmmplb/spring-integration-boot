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

