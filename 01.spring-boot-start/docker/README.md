配合idea自动部署：https://blog.csdn.net/weixin_39098944/article/details/107087257


**curl**
````
curl  --header "Content-Type: application/json" \
-H 'Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNzE1Mzk0NTAyLCJqdGkiOiIwNTNjM2FlZC04Yzg0LTQ2MjItODY3NC1iMTQzZDNhNmVkMDkiLCJjbGllbnRfaWQiOiJkaWNwIn0.KeEwRAak5wulRYI9UU92Xd3geClhbKRUBa0Fj58Muzj4kBIPsXiVfJRMXuOw1wdLVeAPca-27Hfqh2tvvxpPHJ6QxmNFm0v5T0N6l57iEu-11Of7SAedbTN0As5BZgtrglVxjXzyvSS3hk50PrzAJCp3kHqVLlChh6rM4gKP2LgrYWKpM9e9f8BXJ19I9ouTOFinnZ-ufYlGHYwZjeQlv9UdA6e5I_4C-AjOorCZMWgzB9anyftPPDB9QH-iso-QglNnrfTieZ5sp0E7voMHHU4qHreIi3FalZ7lCSJzG3QPr40vMc1O6v6gKbiTYycOm1Kags4NNq5sYPaiplJsHw' \
-H 'Cpm-User-Identity: 053c3aed-8c84-4622-8674-b143d3a6ed09' \
-H 'Cpm-Client-Type: web' \
-d '{ "sysIds": [ "aps" ], "uids": [ "ruyi1", "lvxianglong", "liushihui2" ]}'  \
-X POST http://localhost:40013/platform/auth/userDetails/distributeBatch
````

````
curl \
-H 'Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJvcGVuaWQiXSwiZXhwIjoxNzE1Mzk0NTAyLCJqdGkiOiIwNTNjM2FlZC04Yzg0LTQ2MjItODY3NC1iMTQzZDNhNmVkMDkiLCJjbGllbnRfaWQiOiJkaWNwIn0.KeEwRAak5wulRYI9UU92Xd3geClhbKRUBa0Fj58Muzj4kBIPsXiVfJRMXuOw1wdLVeAPca-27Hfqh2tvvxpPHJ6QxmNFm0v5T0N6l57iEu-11Of7SAedbTN0As5BZgtrglVxjXzyvSS3hk50PrzAJCp3kHqVLlChh6rM4gKP2LgrYWKpM9e9f8BXJ19I9ouTOFinnZ-ufYlGHYwZjeQlv9UdA6e5I_4C-AjOorCZMWgzB9anyftPPDB9QH-iso-QglNnrfTieZ5sp0E7voMHHU4qHreIi3FalZ7lCSJzG3QPr40vMc1O6v6gKbiTYycOm1Kags4NNq5sYPaiplJsHw' \
-H 'Cpm-User-Identity: 053c3aed-8c84-4622-8674-b143d3a6ed09' \
-H 'Cpm-Client-Type: web' \
http://localhost:40013/platform/auth/userDetails/distributedLock
````

在linux通过BUILD命令构建镜像
````
docker build -t spring-boot-start:1.0.0 .
````

运行镜像
````
docker run -p 80:80 -d --name spring-boot-start spring-boot-start:1.0.0
````

我们可以通过如下命令来获取容器的日志地址

docker inspect --format '{{.LogPath}}' 镜像id

然后通过命令查看上述命令找到的日志地址

docker - Dockerfile常用指令
````
指令	                    描述
FROM	                    构建新镜像是基于哪个镜像
MAINTAINER	            进行维护者姓名或邮箱地址
RUN	                    构建镜像时运行的shell命令
ENV	                    设置环境变量
USER	                    为RUN、CMD和ENTRYPOINT执行命令指定运行用户
EXPOSE	                    声明容器运行的服务端口
HEALTHCHECK	            容器中服务器健康检查
WORKDIR	                    为RUN、CMD、ENTRYPOINT、COPY和ADD设置工作目录
ENTRYPOINT	            运行容器时执行，如果有多个CMD指令，最后一个生效
CMD	                    运行容器时执行，如果有多个CMD指令，最后一个生效
ADD	                    添加文件包或文件，带有解压的功能
COPY	                    单纯复制文件，或文件夹
LABEL	                    标签
````
详细内容
````
===================================
FROM        指定基础镜像

构建新镜像需要指定是基于哪个镜像，即指定基础镜像。 此指令通常必需放在Dockerfile文件第一个非注释行，后续的指令都是运行
于此基准镜像所提供的运行环境 。

基础镜像可以是任何可用镜像文件，默认情况下，docker build会在docker主机本地查找指定的镜像文件，在其不存在时，则会从Docker Hub Registry上拉取所需的镜像文件.如果找不到指定的镜像文件，docker build会返回一个错误信息。
使用示例：
FROM centos
FROM ubuntu:bionic
FROM mysql:5.7
===================================
LABEL       指定镜像元数据
可以指定镜像元数据，如: 镜像作者等 

使用示例：

可以在一行中指定多个标签
LABEL multi.label1="value1" multi.label2="value2" other="value3"

也可以在一条指令中指定多个标签
LABEL multi.label1="value1" \
      multi.label2="value2" \
      other="value3"
===================================

RUN         执行 shell命令
RUN 指令用来在构建镜像阶段需要执行 FROM 指定镜像所支持的Shell命令。
通常各种基础镜像一般都支持丰富的shell命令。
注意: RUN 可以写多个，每一个RUN指令都会建立一个镜像层，所以尽可能合并成一条指令,比如将多个shell命令通过 && 连接一起成为在一条指令
每个RUN都是独立运行的,和前一个RUN无关 

使用示例：

#shell 格式：
RUN /bin/bash -c 'source $HOME/.bashrc; echo $HOME'
#exec 格式：
RUN ["/bin/bash", "-c", "echo hello"]
===================================
ENV         设置环境变量 
ENV 可以定义环境变量和值，会被后续指令(如:ENV,ADD,COPY,RUN等)通过$KEY或${KEY}进行引用，并在容器运行时保持 

使用示例：

ENV MY_NAME="John Doe"
ENV MY_DOG=Rex\ The\ Dog
ENV MY_CAT=fluffy
#一次设置多个变量
ENV MY_NAME="John Doe" MY_DOG=Rex\ The\ Dog \
    MY_CAT=fluffy
===================================
COPY        复制文件
COPY 指令将从构建上下文目录中 <源路径> 的文件/目录复制到新的一层的镜像内的 <目标路径> 位置。

使用示例：

COPY test.txt /data/test/
COPY hom* /mydir/
COPY hom?.txt /mydir/
===================================
ADD         复制并解压文件
该命令可认为是增强版的COPY，不仅支持COPY，还支持自动解压缩。

可以将本地文件复制到容器中，tar类型文件会自动解压。也可以访问网络资源，类似wget，但来自远程URL 的资源不会被解压缩。

使用示例：

ADD nginx-1.14.2.tar.gz /usr/local/src/
ADD http://example.com/foobar /data
===================================
CMD         容器启动命令
一个容器中需要持续运行的进程一般只有一个,CMD 用来指定启动容器时默认执行的一个命令，且其运行结束后,容器也会停止,所以一般CMD 指定的命令为持续运行且为前台命令. 

注意：

CMD不同于RUN，CMD用于指定在容器启动时所要执行的命令，而RUN用于指定镜像构建时所要执行的命令。
 如果docker run没有指定任何的执行命令或者dockerfile里面也没有ENTRYPOINT，那么开启容器时就会使用执行CMD指定的默认的命令 
 每个 Dockerfile 只能有一条 CMD 命令。如指定了多条，只有最后一条被执行 
 如果用户启动容器时用 docker run xxx 指定运行的命令，则会覆盖 CMD 指定的命令 
使用示例：

# 使用 exec 执行，推荐方式，第一个参数必须是命令的全路径,此种形式不支持环境变量
CMD ["executable","param1","param2"]
CMD ["nginx", "-g", "daemon off;"]
# 在 /bin/sh 中执行，提供给需要交互的应用；此种形式支持环境变量
CMD command param1 param2
CMD echo "This is a test." | wc -
# 提供给 ENTRYPOINT 命令的默认参数
CMD ["param1","param2"]
CMD ["- -help"]
===================================
ENTRYPOINT      入口点 
 功能类似于CMD，配置容器启动后执行的命令及参数
 

 ENTRYPOINT 不能被 docker run 提供的参数覆盖，而是追加,即如果docker run 命令有参数，那么参数全部都会作为ENTRYPOINT的参数
如果docker run 后面没有额外参数，但是dockerfile中的CMD里有（即上面CMD的第三种用法），即Dockerfile中即有CMD也有ENTRYPOINT,那么CMD的全部内容会作为ENTRYPOINT的参数
如果docker run 后面有额外参数，同时Dockerfile中即有CMD也有ENTRYPOINT,那么docker run后面的参数覆盖掉CMD参数内容,最终作为ENTRYPOINT的参数
可以通过docker run --entrypoint string 参数在运行时替换,注意string不要加空格
使用CMD要在运行时重新写命令本身,然后在后面才能追加运行参数，ENTRYPOINT则可以运行时无需重写命令就可以直接接受新参数
每个 Dockerfile 中只能有一个 ENTRYPOINT，当指定多个时，只有最后一个生效 
使用示例：

ENTRYPOINT [ "curl", "-s","https://ip.cn"]
===================================
ARG: 构建参数 
 ARG指令在build 阶段指定变量,和ENV不同的是，容器运行时不会存在这些环境变量
 

 如果和ENV同名，ENV覆盖ARG变量

可以用 docker build --build-arg <参数名>=<值> 来覆盖 

使用示例：

FROM busybox
ARG user1
ARG buildno
===================================
VOLUME: 匿名卷 
 在容器中创建一个可以从本地主机或其他容器挂载的挂载点，一般用来存放数据库和需要保持的数据等，一般会将宿主机上的目录挂载至VOLUME 指令指定的容器目录。即使容器后期被删除，此宿主机的目录仍会保留，从而实现容器数据的持久保存。 

使用示例：

VOLUME [ "/data1","/data2" ]
===================================
EXPOSE: 暴露端口 
 指定服务端的容器需要对外暴露(监听)的端口号，以实现容器与外部通信。
 EXPOSE 仅仅是声明容器打算使用什么端口而已,并不会真正暴露端口,即不会自动在宿主进行端口映射因此，在启动容器时需要通过 -P 或-p ，Docker 主机才会真正分配一个端口转发到指定暴露的端口才可使用
 即使 Dockerfile没有EXPOSE 端口指令,也可以通过docker run -p 临时暴露容器内程序真正监听的端口,所以EXPOSE 相当于指定默认的暴露端口,可以通过docker run -P 进行真正暴露 
使用示例：

EXPOSE 80
===================================
WORKDIR: 指定工作目录 
 为后续的 RUN、CMD、ENTRYPOINT 指令配置工作目录，当容器运行后，进入容器内WORKDIR指定的默认目录。

 WORKDIR 指定工作目录（或称当前目录），以后各层的当前目录就被改为指定的目录，如该目录不存在，WORKDIR 会自行创建。

使用示例：

WORKDIR /test
===================================
ONBUILD: 子镜像引用父镜像的指令 
 可以用来配置当构建当前镜像的子镜像时，会自动触发执行的指令,但在当前镜像构建时,并不会执行,即延迟到子镜像构建时才执行 

使用示例：

ONBUILD RUN yum -y install tree
===================================
USER: 指定当前用户 
 指定运行容器时的用户名或 UID，后续的 RUN 也会使用指定用户当服务不需要管理员权限时，可以通过该命令指定运行用户这个用户必须是事先建立好的，否则无法切换,如果没有指定 USER,默认是 root 身份执行 

使用示例：

RUN groupadd -r mysql && useradd -r -g mysql mysql
USER mysql
===================================
HEALTHCHECK: 健康检查 
 检查容器的健康性 

HEALTHCHECK [选项] CMD <命令> #设置检查容器健康状况的命令
HEALTHCHECK NONE #如果基础镜像有健康检查指令，使用这行可以屏蔽掉其健康检查指令
HEALTHCHECK 支持下列选项:
--interval=<间隔> #两次健康检查的间隔，默认为 30 秒
--timeout=<时长> #健康检查命令运行超时时间，如果超过这个时间，本次健康检查就被视为失败，默
认 30 秒
--retries=<次数> #当连续失败指定次数后，则将容器状态视为 unhealthy，默认3次
--start-period=<FDURATION> #default: 0s
#检查结果返回值:
0 #success the container is healthy and ready for use
1 #unhealth the container is not working correctly
2 #reserved do not use this exit code

FROM nginx
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
HEALTHCHECK --interval=5s --timeout=3s \
CMD curl -fs http://localhost/ || exit 1
===================================

````