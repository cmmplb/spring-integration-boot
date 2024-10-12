#!/bin/bash
proj_path=$(cd `dirname $0`;pwd)
jarpath=$(find ${proj_path} -maxdepth 1 -name *.jar)
#-Duser.timezone=GMT+08
JAVA_OPTS="-Xms128m -Xmx512m"
jarname=${jarpath##*/}
if [ ! -d "log" ];then
	mkdir log
fi
dates=`date "+%Y.%m.%d"`
logfile="${proj_path}/log/${jarname}.${dates}.out"
echo ${jarpath}
echo ${jarname}

function start()
{
	echo "start"
	nohup java ${JAVA_OPTS} -jar ${jarpath} >> ${logfile} 2>&1 &
	echo "start ${jarname} success"
}

function stop()
{
	jpid=`ps aux|grep -v grep|grep jar|grep -v out|grep ${jarname}|awk '{print $2}'`
	if [ ${jpid} ];then
		echo "Stop Process ${jpid}"
		kill -15 $jpid
	fi

	sleep 5

	jpid=`ps aux|grep -v grep|grep jar|grep -v out|grep ${jarname}|awk '{print $2}'`
	if [ ${jpid} ];then
		echo "Stop Process"
		kill -15 $jpid
	else
		echo "Stop Process Success"
		return 0
	fi

	sleep 5

	jpid=`ps aux|grep -v grep|grep jar|grep -v out|grep ${jarname}|awk '{print $2}'`
	if [ ${jpid} ];then
		echo "Stop FAILD"
	else
		echo "Stop Process Success"
	fi
}

case $1 in
	"start")
		start
	;;
	"stop")
		stop
	;;
	"restart")
		stop
		start
	;;
	*)
		echo "bash run.sh [start|stop|restart]"
	;;
esac
