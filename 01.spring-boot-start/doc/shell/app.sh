#!/bin/bash
## jar包版本号
## 容器启动脚本, app.sh输入三个参数, restart:重启, java-demo-two：项目名称, 1.0.0：jar包版本号, 则jar包名称为java-demo-two-1.0.0.jar
# sh /data/app.sh restart java-demo-two 1.0.0

version=$3
day=$(date '+%Y-%m-%d')
##app名称, 如java-demo-two、java-demo-one
appName=$2
if [ -z $appName ];then
    appName=`ls -t |grep .jar$ |head -n1`
fi

function start()
{
	count=`ps -ef |grep java|grep $appName|wc -l`
	if [ $count != 0 ];then
		echo "Maybe $appName is running, please check it..."
	else
		echo "The $appName is starting..."
		java -jar /data/$appName-$version.jar --spring.profiles.active=test -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -Xms512M -Xmx4G > /logs/$appName/$day.log 2>&1
	fi
}

function stop()
{
	appId=`ps -ef |grep java|grep $appName|awk '{print $2}'`
	if [ -z $appId ];then
	    echo "Maybe $appName not running, please check it..."
	else
        echo "The $appName is stopping..."
        kill -9 $appId
	fi
}

function restart()
{

	echo "The appName is $appName"
    stop
    for i in {5..1}
    do
        echo -n "$i "
        sleep 1
    done
    echo 0



    start
}

function backup()
{
    # get backup version
    backupApp=`ls |grep -wv $releaseApp$ |grep .jar$`

    # create backup dir
    if [ ! -d "backup" ];then
        mkdir backup
    fi

    # backup
    for i in ${backupApp[@]}
    do
        echo "backup" $i
        mv $i backup
    done
}

function status()
{
    appId=`ps -ef |grep java|grep $appName|awk '{print $2}'`
	if [ -z $appId ]
	then
	    echo -e "\033[31m Not running \033[0m"
	else
	    echo -e "\033[32m Running [$appId] \033[0m"
	fi
}


function usage()
{
    echo "Usage: $0 {start|stop|restart|status|stop -f}"
    echo "Example: $0 start"
    exit 1
}

case $1 in
	start)
	start;;

	stop)
	stop;;

	restart)
	restart;;

	status)
	status;;

	*)
	usage;;
esac

