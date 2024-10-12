# apiVersion

1. extensions/v1beta1是用于kubernetes版本在1.6之前, 资源申明 
2. apps/v1beta1是用于1.6-1.9版本之间 
3. apps/v1是1.9版本以后使用

kubectl api-versions

admissionregistration.k8s.io/v1
apiextensions.k8s.io/v1
apiregistration.k8s.io/v1
apps/v1
authentication.k8s.io/v1
authorization.k8s.io/v1
autoscaling/v1
autoscaling/v2
autoscaling/v2beta1
autoscaling/v2beta2
batch/v1
batch/v1beta1
certificates.k8s.io/v1
coordination.k8s.io/v1
discovery.k8s.io/v1
discovery.k8s.io/v1beta1
events.k8s.io/v1
events.k8s.io/v1beta1
flowcontrol.apiserver.k8s.io/v1beta1
flowcontrol.apiserver.k8s.io/v1beta2
networking.k8s.io/v1
node.k8s.io/v1
node.k8s.io/v1beta1
policy/v1
policy/v1beta1
rbac.authorization.k8s.io/v1
scheduling.k8s.io/v1
storage.k8s.io/v1
storage.k8s.io/v1beta1
v1

````
Kind	                                                   apiVersion
CertificateSigningRequest	                                certificates.k8s.io/v1beta1
ClusterRoleBinding	                                        rbac.authorization.k8s.io/v1
ClusterRole	                                                rbac.authorization.k8s.io/v1
ComponentStatus	                                            v1
ConfigMap	                                                v1
ControllerRevision	                                        apps/v1
CronJob                                             	    batch/v1beta1
DaemonSet	                                                extensions/v1beta1
Deployment	                                                extensions/v1beta1
Endpoints	                                                v1
Event	                                                    v1
HorizontalPodAutoscaler	                                    autoscaling/v1
Ingress	                                                    extensions/v1beta1
Job	                                                        batch/v1
LimitRange	                                                v1
Namespace	                                                v1
NetworkPolicy	                                            extensions/v1beta1
Node	                                                    v1
PersistentVolumeClaim	                                    v1
PersistentVolume	                                        v1
PodDisruptionBudget	                                        policy/v1beta1
Pod	                                                        v1
PodSecurityPolicy	                                        extensions/v1beta1
PodTemplate	                                                v1
ReplicaSet	                                                extensions/v1beta1
ReplicationController	                                    v1
ResourceQuota	                                            v1
RoleBinding	                                                rbac.authorization.k8s.io/v1
Role	                                                    rbac.authorization.k8s.io/v1
Secret	                                                    v1
ServiceAccount	                                            v1
Service	                                                    v1
StatefulSet	                                                apps/v1
````

# Pod

## Pod的资源清单

````yaml
apiVersion: v1     #必选, 版本号, 例如v1
kind: Pod          #必选, 资源类型, 例如 Pod
metadata: 　 #必选, 元数据
  name: string     #必选, Pod名称
  namespace: string  #Pod所属的命名空间,默认为"default"
  labels: 　　  #自定义标签列表
            - name: string      　
spec: #必选, Pod中容器的详细定义
  containers: #必选, Pod中容器列表
    - name: string   #必选, 容器名称
      image: string  #必选, 容器的镜像名称
      imagePullPolicy: [ Always|Never|IfNotPresent ]  #获取镜像的策略  
      # - Always：总是从远程仓库拉取镜像（一直远程下载） 
      # - IfNotPresent：本地有则使用本地镜像, 本地没有则从远程仓库拉取镜像（本地有就本地 本地没远程下载）
      # - Never：只使用本地镜像, 从不去远程仓库拉取, 本地没有就报错 （一直使用本地）
      # 如果镜像tag为具体版本号,  默认策略是：IfNotPresent
      # 如果镜像tag为：latest（最终版本） , 默认策略是always
      command: [ string ]   #容器的启动命令列表, 如不指定, 使用打包时使用的启动命令
      args: [ string ]      #容器的启动命令参数列表
      workingDir: string  #容器的工作目录
      volumeMounts: #挂载到容器内部的存储卷配置
        - name: string      #引用pod定义的共享存储卷的名称, 需用volumes[]部分定义的的卷名
          mountPath: string #存储卷在容器内mount的绝对路径, 应少于512字符
          readOnly: boolean #是否为只读模式
      ports: #需要暴露的端口库号列表
        - name: string        #端口的名称
          containerPort: int  #容器需要监听的端口号
          hostPort: int       #容器所在主机需要监听的端口号, 默认与Container相同
          protocol: string    #端口协议, 支持TCP和UDP, 默认TCP
      env: #容器运行前需设置的环境变量列表
        - name: string  #环境变量名称
          value: string #环境变量的值
      resources: #资源限制和请求的设置
        limits: #资源限制的设置
          cpu: string     #Cpu的限制, 单位为core数, 将用于docker run --cpu-shares参数
          memory: string  #内存限制, 单位可以为Mib/Gib, 将用于docker run --memory参数
        requests: #资源请求的设置
          cpu: string    #Cpu请求, 容器启动的初始可用数量
          memory: string #内存请求,容器启动的初始可用数量
      lifecycle: #生命周期钩子
        postStart: #容器启动后立即执行此钩子,如果执行失败,会根据重启策略进行重启
        preStop: #容器终止前执行此钩子,无论结果如何,容器都会终止
      livenessProbe: #对Pod内各容器健康检查的设置, 当探测无响应几次后将自动重启该容器
        exec: 　 #对Pod容器内检查方式设置为exec方式
          command: [ string ]  #exec方式需要制定的命令或脚本
        httpGet: #对Pod内个容器健康检查方法设置为HttpGet, 需要制定Path、port
          path: string
          port: number
          host: string
          scheme: string
          HttpHeaders:
            - name: string
              value: string
        tcpSocket: #对Pod内个容器健康检查方式设置为tcpSocket方式
          port: number
          initialDelaySeconds: 0       #容器启动完成后首次探测的时间, 单位为秒
          timeoutSeconds: 0    　　    #对容器健康检查探测等待响应的超时时间, 单位秒, 默认1秒
          periodSeconds: 0     　　    #对容器监控检查的定期探测时间设置, 单位秒, 默认10秒一次
          successThreshold: 0
          failureThreshold: 0
          securityContext:
            privileged: false
  restartPolicy: [ Always | Never | OnFailure ]  #Pod的重启策略
  nodeName: <string> #设置NodeName表示将该Pod调度到指定到名称的node节点上
  nodeSelector: obeject #设置NodeSelector表示将该Pod调度到包含这个label的node上
  imagePullSecrets: #Pull镜像时使用的secret名称, 以key：secretkey格式指定
    - name: string
  hostNetwork: false   #是否使用主机网络模式, 默认为false, 如果设置为true, 表示使用宿主机网络
  volumes: #在该pod上定义共享存储卷列表
    - name: string    #共享存储卷名称 （volumes类型有很多种）
      emptyDir: { }       #类型为emtyDir的存储卷, 与Pod同生命周期的一个临时目录。为空值
      hostPath: string   #类型为hostPath的存储卷, 表示挂载Pod所在宿主机的目录
        path: string      　　        #Pod所在宿主机的目录, 将被用于同期中mount的目录
      secret: 　　　#类型为secret的存储卷, 挂载集群与定义的secret对象到容器内部
        scretname: string
        items:
          - key: string
            path: string
      configMap: #类型为configMap的存储卷, 挂载预定义的configMap对象到容器内部
        name: string
        items:
          - key: string
            path: string
````

# Pod控制器

## Deployment的资源清单

常用这个, 其他的后面用到再列出

````yaml
apiVersion: apps/v1 # 版本号
kind: Deployment # 类型       
metadata: # 元数据
  name: # rs名称 
  namespace: # 所属命名空间 
  labels: #标签
    controller: deploy
spec: # 详情描述
  replicas: 3 # 副本数量
  revisionHistoryLimit: 3 # 保留历史版本
  paused: false # 暂停部署, 默认是false
  progressDeadlineSeconds: 600 # 部署超时时间（s）, 默认是600
  strategy: # 策略
    type: RollingUpdate # 滚动更新策略
    rollingUpdate: # 滚动更新
      maxSurge: 30% # 最大额外可以存在的副本数, 可以为百分比, 也可以为整数
      maxUnavailable: 30% # 最大不可用状态的 Pod 的最大值, 可以为百分比, 也可以为整数
  selector: # 选择器, 通过它指定该控制器管理哪些pod
    matchLabels: # Labels匹配规则
      app: nginx-pod
    matchExpressions: # Expressions匹配规则
      - { key: app, operator: In, values: [ nginx-pod ] }
  template: # 模板, 当副本数量不足时, 会根据下面的模板创建pod副本
    metadata:
      labels:
        app: nginx-pod
    spec:
      containers:
        - name: nginx
          image: nginx:1.17.1
          ports:
            - containerPort: 80
````

# Service

## Service的资源清单

````yaml
kind: Service  # 资源类型
apiVersion: v1  # 资源版本
metadata: # 元数据
  name: service # 资源名称
  namespace: cmmplb # 命名空间
spec: # 描述
  selector: # 标签选择器, 用于确定当前service代理哪些pod
    app: nginx
  type: # Service类型, 指定service的访问方式
  clusterIP:  # 虚拟服务的ip地址
  sessionAffinity: # session亲和性, 支持ClientIP、None两个选项
  ports: # 端口信息
    - protocol: TCP
      port: 3017  # service端口
      targetPort: 5003 # pod端口
      nodePort: 31122 # 主机端口
````

# 存储

## PV资源清单

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv2
spec:
  nfs: # 存储类型, 与底层真正存储对应
  capacity: # 存储能力, 目前只支持存储空间的设置
    storage: 2Gi
  accessModes:  # 访问模式
  storageClassName: # 存储类别
  persistentVolumeReclaimPolicy: # 回收策略
```

## PVC资源清单

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pvc
  namespace: dev
spec:
  accessModes: # 访问模式
  selector: # 采用标签对PV选择
  storageClassName: # 存储类别
  resources: # 请求空间
    requests:
      storage: 5Gi
```