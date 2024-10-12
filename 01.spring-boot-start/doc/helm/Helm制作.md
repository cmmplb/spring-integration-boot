开始创作
通过上一节的内容介绍, 我们已经了解了Helm模板的一些基本语法, 回顾我们在“准备工作和目标”章节中的目标：简化安装过程, 同时更好的管理配置中存在关联关系的配置值。最后, deployment的后期维护和升级也能通过Helm命令进行。既是把“准备工作和目标”章节中所列的K8s配置标红部分, 通过模板语法进行替换, 再增加namespace.yaml模板配置, 我们的Chart安装包就制作完成了。

具体步骤：1. 首先删除myChart工程下template/目录下的所有目录和文件；2. 在template目录下创建名为namesapce.yaml、configmap.yaml、deployment.yaml模板配置文件, 内容如下：

namespace.yaml
````
apiVersion: v1 kind: Namespace metadata:
name: {{ .Values.namespace }} configmap.yaml

apiVersion: v1 kind: ConfigMap metadata:
name: {{ .Values.volumes.application }} namespace: {{ .Values.namespace }} data:
application.yml: | server:
port: {{ .Values.service.targetPort }} servlet:
context-path: /api/order/v1 spring:
application:

# 服务的实例名, 服务间的调用通过此名字调用

name: {{ .Values.nameOverride }} datasource:
url: jdbc:mysql://{{ .Values.config.mysql.server
}}/cnp-order?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true
driver-class-name: com.mysql.cj.jdbc.Driver username: {{ .Values.config.mysql.user }} password: {{
.Values.config.mysql.password }} deployment.yaml

apiVersion: apps/v1 kind: Deployment metadata:
name: {{ .Values.nameOverride }} namespace: {{ .Values.namespace }} spec:
{{- if not .Values.autoscaling.enabled }} replicas: {{ .Values.replicaCount }} {{- end }} selector:
matchLabels:
app: cnp-order template:
metadata:
labels:
app: cnp-order spec:
volumes:

- name: {{ .Values.volumes.application }} configMap:
  name: {{ .Values.volumes.application }} containers:
- name: {{ .Values.nameOverride }} image: "{{ .Values.image.repository }}:{{ .Values.image.tag | default
  .Chart.AppVersion }}"
  imagePullPolicy: {{ .Values.image.pullPolicy }} volumeMounts:
- mountPath: /apps/conf/cnp-order/application.yml name: {{ .Values.volumes.application }} subPath: application.yml
````
3. 编辑工程下的values.yaml文件, 内容如下：
````
replicaCount: 1 namespace: paas-cnp volumes:
application: order-application image:
repository: cnp-order
# Overrides the image tag whose default is the Chart appVersion.

tag: 1.0.0 pullPolicy: IfNotPresent imagePullSecrets: []
nameOverride: cnp-order fullnameOverride: ""
autoscaling:
enabled: false minReplicas: 1 maxReplicas: 5 targetCPUUtilizationPercentage: 80

# targetMemoryUtilizationPercentage: 80

nodeSelector:
app: cnp-order

config:
mysql:
server: {ip} user: root password: root 调试校验 我们可以在myChart/同级目录, 使用命令：
````
$ Helm lint myChart/ 进行语法校验, 若我们使用上述的所有配置, 执行后结果如图1-10所示：

图1-10 语法校验结果 我们在values.yaml中增加如下配置:
````
service:
targetPort: 9094
````
再次执行Helm lint myChart/ 校验通过, 结果如图1-11所示：

图1-11 语法校验通过图 我们也可以通过命令：

$ Helm template myChart/ --output-dir ./result

该命令会在myChart同级目录创建一个result目录, 该目录内会存在模板渲染之后的配置文件, 我们可以通过检查这些文件内容结合命令行的提示信息判断错误发生位置。

接着, 可以进行一个预安装校验, 命令如下：

$ Helm install --dry-run --debug cnp-order ./myChart 预安装会模拟正式的安装流程, 但不会在环境上真正部署安装包。

安装和后续升级 通过预安装之后, 当我们想要部署我们的工程时, 只需要使用命令：

$ Helm install [release-name] myChart 即可完成应用的安装了。

但有个遗留问题, values.yaml 中包含了默认的安装参数, 但是诸如数据库的ip、username、password, 若我们不想去修改安装包, 如何在安装的时候进行覆盖呢？我们只要在 install 时使用 set
选项, 设置想要覆盖的参数值即可。

$ Helm install myChart-test myChart--set config.mysql.server=100.71.32.11 用户也可以在安装时指定自己的values.yaml配置。例如用户在升级的时候用
upgrade 命令, 指定新的参数配置文件, 即可实现在原有部署好的应用的基础上变更配置。命令如下：

$ Helm install myChart-test02 myChart -f my-values.yaml $ Helm upgrade myChart-test02 myChart -f my-new-values.yaml

六、总结

我们可以看出Helm作为K8s的“yum工具”, 确实可以解决前言背景所提到的用户和实施在部署和升级应用时所遇到的问题, 并且研发人员在K8s配置文件已经存在的基础上再进行Helm安装包的制作, 并不会有太多的改造量。通过Helm把一个应用所包含的yaml进行成套的管理, 为我们后续安装维护带来了巨大的便利性。


