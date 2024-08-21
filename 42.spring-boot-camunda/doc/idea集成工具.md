# 软件

下载地址

https://camunda.com/download/modeler/

idea：

file -> Setting -> Tools -> External Tools

添加下载的路径，Program 为应用程序路径

Workings Directory 为文件夹路径

设置以后右键选中bpmn文件，点击idea最上端tools ->External Tools 选中camunda

# docker

https://github.com/camunda/camunda-platform

Step 1: Download or clone this repo. You may already have it.

Step 2: Open the `docker-compose/camunda-8.6` folder. This contains the alpha releases for the upcoming 8.6 release.

Step 3: Run

docker compose --profile modeling up -d
Step 4: Open Web Modeler at localhost:8070

这里下载的是camunda-modeler-5.26.0-mac-arm64.zip

