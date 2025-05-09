下载head插件：https://github.com/mobz/elasticsearch-head

npm install 


npm run start

其他安装错误（因为在安装中没有截图, 又都解决了, 所以没有问题截图的复现）：

报错：Failed at the elasticsearch-head@0.0.0 start script.

npm install grunt --save-dev
####再次执行
grunt server
如果弹出提醒安装文件

依次执行：npm install grunt-contrib-clean

## 跨域问题
访问http://localhost:9100/

修改elasticsearch/config下的配置文件：elasticsearch.yml, 增加以下三句命令：
http.cors.enabled: true
http.cors.allow-origin: "*"
network.host: 127.0.0.1 # 容器：network.host: 0.0.0.0
其中：
http.cors.enabled: true：此步为允许elasticsearch跨域访问, 默认是false。
http.cors.allow-origin: "*"：表示跨域访问允许的域名地址（*表示任意）。
network.host:127.0.0.1：主机域名