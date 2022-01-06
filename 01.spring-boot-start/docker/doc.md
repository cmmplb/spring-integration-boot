
####docker

生成镜像 docker build  -t ImageName:TagName dir（如果没有java镜像，会先下载镜像后生成）
   
````
.是当前目录   spring-boot-start是生成的镜像名
docker build -t spring-boot-start:1.0.0 .  

选项
-t − 给镜像加一个Tag
ImageName − 给镜像起的名称
TagName − 给镜像的Tag名
Dir − Dockerfile所在目录
````
运行镜像 docker run [OPTIONS] IMAGE[:TAG] [COMMAND] [ARG...]
````
第一个name为运行镜像的名称，后一个为要运行的镜像
docker network inspect 网络名称
docker run -d -p 80:80--network=cmmplb --name spring-boot-start spring-boot-start:1.0.0 
# 重启自动启动 
docker run -d --restart=always --name spring-boot-start -p 80:80 spring-boot-start:1.0.0  

通过 --net 参数来指定容器的网络配置，有4个可选值：
–net=bridge 这个是默认值，连接到默认的网桥。
–net=host 共享主机网络空间，可以像普通进程一样进行通信
–net=container:NAME_or_ID 让 Docker 和已存在的容器共享 IP 地址和端口等网络资源，两者进程可以直接通过 lo 环回接口通信。
–net=none 让 Docker 将新容器放到隔离的网络栈中，但是不进行网络配置。之后，用户可以自己进行配置。

选项说明：
-d, --detach=false， 指定容器运行于前台还是后台，默认为false
-i, --interactive=false， 打开STDIN，用于控制台交互
-t, --tty=false， 分配tty设备，该可以支持终端登录，默认为false
-u, --user=""， 指定容器的用户
-a, --attach=[]， 登录容器（必须是以docker run -d启动的容器）
-w, --workdir=""， 指定容器的工作目录
-c, --cpu-shares=0， 设置容器CPU权重，在CPU共享场景使用
-e, --env=[]， 指定环境变量，容器中可以使用该环境变量
-m, --memory=""， 指定容器的内存上限
-P, --publish-all=false， 指定容器暴露的端口
-p, --publish=[]， 指定容器暴露的端口
-h, --hostname=""， 指定容器的主机名
-v, --volume=[]， 给容器挂载存储卷，挂载到容器的某个目录
--volumes-from=[]， 给容器挂载其他容器上的卷，挂载到容器的某个目录
--cap-add=[]， 添加权限，权限清单详见：http://linux.die.net/man/7/ca...
--cap-drop=[]， 删除权限，权限清单详见：http://linux.die.net/man/7/ca...
--cidfile=""， 运行容器后，在指定文件中写入容器PID值，一种典型的监控系统用法
--cpuset=""， 设置容器可以使用哪些CPU，此参数可以用来容器独占CPU
--device=[]， 添加主机设备给容器，相当于设备直通
--dns=[]， 指定容器的dns服务器
--dns-search=[]， 指定容器的dns搜索域名，写入到容器的/etc/resolv.conf文件
--entrypoint=""， 覆盖image的入口点
--env-file=[]， 指定环境变量文件，文件格式为每行一个环境变量
--expose=[]， 指定容器暴露的端口，即修改镜像的暴露端口
--link=[]， 指定容器间的关联，使用其他容器的IP、env等信息
--lxc-conf=[]， 指定容器的配置文件，只有在指定--exec-drive
--name=""， 指定容器名字，后续可以通过名字进行容器管理，links特性需要使用名字
--privileged=false， 指定容器是否为特权容器，特权容器拥有所有的capabilities
--restart="no"， 指定容器停止后的重启策略:
      no：容器退出时不重启
      on-failure：容器故障退出（返回值非零）时重启
      always：容器退出时总是重启


--rm=false， 指定容器停止后自动删除容器(不支持以docker run -d启动的容器)
--sig-proxy=true， 设置由代理接受并处理信号，但是SIGCHLD、SIGSTOP和SIGKILL不能被代理
````

####docker-compose
检查配置是否正确
docker-compose config

启动容器服务
docker-compose up

停止并删除容器服务
docker-compose down

强制启动重新建立容器
docker-compose up -d --force-recreate

docker-compose配置,除了使用image指定镜像之外,还可以使用build把构建镜像和启动容器放在一起执行.

````
version: '3'
services:
  server:
    build: ./data
    image: server:1.0.0
    container_name: server
    restart: always
    ports:
      - 80:80
build: 指定构建目录,可以使用相对路径
image: 跟在build后面,指定构建镜像的名字
````

重新构建:
docker-compose build 或者docker-compose up --build