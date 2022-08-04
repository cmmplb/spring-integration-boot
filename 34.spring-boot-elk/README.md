在logstash中安装json_lines插件

# 进入logstash容器	
docker exec -it logstash /bin/bash	
# 进入bin目录	
cd /bin/	
# 安装插件	
logstash-plugin install logstash-codec-json_lines	
# 退出容器	
exit	
# 重启logstash服务	
docker restart logstash
