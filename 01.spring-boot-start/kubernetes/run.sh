#!/usr/bin/env bash

function start()
{
	echo '=====运行====='
	kubectl apply -f springboot-start-service-deployment.yaml
}
function stop()
{
	echo '=====停止====='
	kubectl delete -f springboot-start-service-deployment.yaml
}
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