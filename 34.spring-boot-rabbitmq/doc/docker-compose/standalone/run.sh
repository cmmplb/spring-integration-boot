#!/usr/bin/env bash

function start()
{
	echo '=====运行====='
	docker-compose -f docker-compose.yml up -d
}
function stop()
{
	echo '=====停止====='
	docker-compose -f docker-compose.yml down
}
function restart()
{
	echo '=====重启====='
	docker-compose -f docker-compose.yml restart
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






