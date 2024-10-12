#!/usr/bin/env bash

containerName=spring-boot-start
image=registry.cn-hangzhou.aliyuncs.com/cmmplb/spring-boot-start:1.0.0
prot=80:80

function start()
{
	echo '=====运行====='
	docker run -d --restart=always --name ${containerName} -p ${prot} ${image}
}
function stop()
{
	echo '=====停止====='
	docker stop ${containerName}
	docker rm ${containerName}
}
# docker-compose -f docker-compose.yml restart
function restart()
{
	echo '=====重启====='
	stop
	start
}
case $1 in
	"start")
		start
	;;
	"stop")
		stop
	;;
	"restart")
		restart
	;;
	*)
		echo "bash run.sh [start|stop|restart]"
	;;
esac






