#! /bin/bash

#=====================================================================
# 应用服务停止的 shell 脚本
# 通过项目名称查找到PID
# 然后 kill -9 pid
#=====================================================================

# 项目名称
APPLICATION="spring-boot-start"

if [[ -z "$1" ]]
then
    pid=`ps ax |grep -i 'spring-boot-start.jar' |grep java | grep -v grep |  awk '{print $1}'`
else
    pid=`ps ax |grep -i 'spring-boot-start.jar' |grep java | grep -i 'server.port='''1''''| grep -v grep |  awk '{print $1}'`
fi

if [[ -z "$pid" ]] ; then
    echo "${APPLICATION} 服务并没有运行."
    exit 0;
fi

kill -9 ${pid}
echo "已成功关闭了 ${APPLICATION} 服务 (PID: ${pid})."
