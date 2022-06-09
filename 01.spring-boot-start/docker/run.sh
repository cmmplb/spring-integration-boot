#!/usr/bin/env bash

function start()
{
	echo '=====运行====='
	/usr/local/bin/docker-compose -f docker-compose.yml up -d
}
function stop()
{
	echo '=====停止====='
	/usr/local/bin/docker-compose -f docker-compose.yml down
}
# /usr/local/bin/docker-compose -f docker-compose.yml restart
function restart()
{
	echo '=====重启====='
	/usr/local/bin/docker-compose -f docker-compose.yml down
	/usr/local/bin/docker-compose -f docker-compose.yml up -d
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






